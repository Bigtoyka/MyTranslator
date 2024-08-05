package com.example.translator.service;

import com.example.translator.model.TranslationRequests;
import com.example.translator.repository.TranslationRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Сервис для выполнения перевода и сохранения результатов в базе данных
@Service
public class InternalTranslationService {
    private final ExternalTranslationService externalTranslationService;
    private final TranslationRequestRepository translationRequestRepository;
    private final ThreadPoolTaskExecutor executor;
    public InternalTranslationService(ExternalTranslationService externalTranslationService,
                                      TranslationRequestRepository translationRequestRepository) {
        this.externalTranslationService = externalTranslationService;
        this.translationRequestRepository = translationRequestRepository;
        this.executor = new ThreadPoolTaskExecutor();
        this.executor.setMaxPoolSize(10); // кол-во потоков
        this.executor.initialize();
    }
    public String translate(String input, String sourceLang, String targetLang, String ipAddress) {
        String[] words = input.split(" ");
        List<CompletableFuture<String>> futures = Stream.of(words)
                .map(
                        word -> CompletableFuture.supplyAsync(
                        () -> externalTranslationService.translateWord(word, sourceLang, targetLang), executor)
                ).toList();
        List<String> translatedWords = futures.stream()
                .map(this::getFutureResult)
                .collect(Collectors.toList());

        String translatedString = "http " + HttpStatus.OK.value() + " " + String.join(" ", translatedWords) ;

        TranslationRequests request = new TranslationRequests(
                null, ipAddress, input, translatedString, LocalDateTime.now()
        );
        translationRequestRepository.save(request);
        return translatedString;
    }

    private String getFutureResult(CompletableFuture<String> future) {
        try {
            return future.get(); // Ожидание результата
        } catch (Exception e) {
            System.err.println("Не удалось получить результат перевода: " + e.getMessage());
            return "";
        }
    }
}
