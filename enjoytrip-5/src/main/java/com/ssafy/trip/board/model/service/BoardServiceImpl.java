package com.ssafy.trip.board.model.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.FileDto;
import com.ssafy.trip.board.model.mapper.BoardMapper;
import com.ssafy.trip.util.BoardSize;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {
	private final BoardMapper boardMapper;
	
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	@Override
	@Transactional
	public int writeArticle(BoardDto boardDto) throws Exception {
		
		boardMapper.writeArticle(boardDto);
		return boardDto.getBoardId();
		
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if("userid".equals(key))
			key = "b.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("boardTypeId", Integer.parseInt(map.get("boardTypeId")));

		return boardMapper.listArticle(param);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int listSize = BoardSize.LIST.getBoardSize();
		int navigationSize = BoardSize.NAVIGATION.getBoardSize();
//		System.out.println(listSize + "   " + navigationSize);
//		int naviSize = SizeConstant.NAVIGATION_SIZE;
//		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(navigationSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / listSize + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= navigationSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / navigationSize * navigationSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		System.out.println("service!!");
		System.out.println(boardDto.getBoardId());
		System.out.println(boardDto.getContent());
		boardMapper.modifyArticle(boardDto);
		boardMapper.deleteFile(boardDto.getBoardId());	// 파일 모두 지우
		
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출

		boardMapper.deleteArticle(articleNo);
		boardMapper.deleteFile(articleNo);

	}
	@Override
	public void uploadFile(List<FileDto> fileDtos) throws Exception {
		boardMapper.uploadFile(fileDtos);
		
	}
	
	@Override
	public List<String> saveFiles(List<FileDto> fileDtos) throws Exception {
	    List<String> filePaths = new ArrayList<>();

	    // 사용자의 홈 디렉토리 기반으로 업로드 경로 설정
	    String userHome = System.getProperty("user.home");
	    String uploadDir = Paths.get(userHome, "enjoytrip", "uploads").toString();

	    for (FileDto fileDto : fileDtos) {
	        // 게시글 ID로 디렉토리 설정
	        String boardIdDir = Paths.get(uploadDir, String.valueOf(fileDto.getBoardId())).toString();
	        File boardDirectory = new File(boardIdDir);

	        // 디렉토리가 없으면 생성
	        if (boardDirectory.exists() && !boardDirectory.isDirectory()) {
	            throw new IOException("디렉토리 경로에 동일한 이름의 파일이 존재합니다: " + boardIdDir);
	        }
	        if (!boardDirectory.exists() && !boardDirectory.mkdirs()) {
	            throw new IOException("디렉토리 생성 실패: " + boardIdDir);
	        }

	        try {
	            // Base64 데이터에서 프로토콜 헤더 제거
	            String base64Data = fileDto.getFileUrl().replaceFirst("^data:image/\\w+;base64,", "");

	            // Base64 디코딩
	            byte[] fileData = Base64.getDecoder().decode(base64Data);

	            // 고유 파일 이름 생성
	            String fileName = UUID.randomUUID().toString() + ".jpg";
	            String filePath = Paths.get(boardIdDir, fileName).toString();

	            // 파일 저장
	            try (OutputStream outputStream = new FileOutputStream(filePath)) {
	                outputStream.write(fileData);
	            }

	            // 저장된 파일 경로 리스트에 추가
	            filePaths.add(filePath);

	        } catch (IllegalArgumentException e) {
	            throw new Exception("잘못된 Base64 데이터: " + fileDto.getFileUrl(), e);
	        } catch (IOException e) {
	            throw new IOException("파일 저장 실패: " + fileDto.getFileUrl(), e);
	        }
	    }

	    return filePaths;
	}
	
	@Override
	public void saveFilestoDatabase(List<FileDto> fileDtos) throws Exception {
		List<String> filePaths = saveFiles(fileDtos);

        // 데이터베이스에 저장
        for (int i = 0; i < fileDtos.size(); i++) {
            fileDtos.get(i).setFileUrl(filePaths.get(i));
        }
        boardMapper.uploadFile(fileDtos);
	}
	@Override
	public List<String> getfiles(int articleno) throws Exception {
		return boardMapper.getFiles(articleno);
		
	}
	
	

}

