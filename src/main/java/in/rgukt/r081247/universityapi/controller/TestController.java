package in.rgukt.r081247.universityapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public String helloWorld() {
		return "Hello World!";
	}

}
