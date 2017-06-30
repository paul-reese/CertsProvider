package io.pivotal.CertsProvider;

import org.cloudfoundry.security.CloudFoundryContainerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Security;

@SpringBootApplication
@RestController
public class CertsProviderApplication {

    @RequestMapping("/")
    public String restCall() {
        String testJson = new RestTemplate().getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
        System.out.println(testJson);

        return testJson;
    }

	public static void main(String[] args) {
		SpringApplication.run(CertsProviderApplication.class, args);

		Security.insertProviderAt(new CloudFoundryContainerProvider(), 2);

        //String s = new RestTemplate().getForObject("https://credhub.diego.security.cf-app.com:8844/api/v1/data>paths=true", String.class);

	}
}
