package com.ssafy.trip.search.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${spring.ai.openai.chat.options.model}")
    private String openaiModel;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public List<String> getRecommendations(String region) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        String command =
        		"이 지역과 관련된 인기 여행지 5개와 짧은 소개, 유명한 맛집 두개를 알려주세요.\n"
        		+ "\n"
        		+ "출력 형식:\n"
        		+ " : 관련된 인기 여행지 Top 5 입니다.\n"
        		+ "1. 여행지 1: 짧은 소개 \n"
        		+ "2. 여행지 2: 짧은 소개 \n"
        		+ "3. 여행지 3: 짧은 소개 \n"
        		+ "4. 여행지 4: 짧은 소개 \n"
        		+ "5. 여행지 5: 짧은 소개 \n"
        		+ "맛집 소개: 한 문장 소개\n"
        		+ "맛집 소개: 한 문장 소개\n";
        // 요청 본문 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openaiModel); // application.properties에서 설정된 모델 사용
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", "You are an expert in travel recommendations."),
            Map.of("role", "user", "content", "추천받은 지역: " + region + command)
        ));
        requestBody.put("temperature", 0.7); // 고정 값 또는 설정에서 로드
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // OpenAI API 호출
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
            OPENAI_API_URL,
            HttpMethod.POST,
            requestEntity,
            Map.class
        );

        // 응답 처리
        Map<String, Object> responseBody = responseEntity.getBody();
        if (responseBody == null) {
            throw new IllegalStateException("응답 본문이 비어 있습니다.");
        }

        // "choices" 키로부터 데이터 가져오기
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new IllegalStateException("OpenAI 응답에서 'choices' 데이터가 비어 있습니다.");
        }

        // "message" 키에서 "content" 추출
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        if (message == null) {
            throw new IllegalStateException("'message' 데이터가 없습니다.");
        }

        String responseContent = (String) message.get("content");
        if (responseContent == null || responseContent.isEmpty()) {
            throw new IllegalStateException("응답 내용이 비어 있습니다.");
        }

        // 결과를 줄 단위로 분리하여 리스트로 반환
        return List.of(responseContent.split("\n"));
    }
}