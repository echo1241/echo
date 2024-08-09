// package com.echo.echo.domain.video;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.reactive.function.client.WebClient;
//
// import reactor.core.publisher.Mono;
//
// @RestController
// @RequestMapping("")
// public class VideoController {
//
// 	@Value("${openvidu.url}")
// 	private String openviduUrl;
//
// 	@Value("${openvidu.secret}")
// 	private String openviduSecret;
//
// 	private final WebClient webClient;
//
// 	public VideoController(WebClient.Builder webClientBuilder) {
// 		this.webClient = webClientBuilder.baseUrl(openviduUrl).build();
// 	}
//
// 	@PostMapping("/create")
// 	public Mono<ResponseEntity<String>> createSession() {
// 		return webClient.post()
// 			.uri("/api/sessions")
// 			.headers(headers -> headers.setBasicAuth("OPENVIDUAPP", openviduSecret))
// 			.retrieve()
// 			.bodyToMono(String.class)
// 			.map(response -> ResponseEntity.ok(response))
// 			.onErrorResume(
// 				e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())));
// 	}
//
// 	@PostMapping("/generate-token")
// 	public Mono<ResponseEntity<String>> generateToken(@RequestParam String sessionId) {
// 		return webClient.post()
// 			.uri("/api/tokens")
// 			.headers(headers -> headers.setBasicAuth("OPENVIDUAPP", openviduSecret))
// 			.bodyValue("{\"session\": \"" + sessionId + "\"}")
// 			.retrieve()
// 			.bodyToMono(String.class)
// 			.map(response -> ResponseEntity.ok(response))
// 			.onErrorResume(
// 				e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage())));
// 	}
// }
//
