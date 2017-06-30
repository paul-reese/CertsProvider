package io.pivotal.CertsProvider;

import org.cloudfoundry.security.CloudFoundryContainerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CertsProviderApplication {

    @RequestMapping("/")
    public String restCall() {
        String testJson = new RestTemplate().getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
        System.out.println(testJson);

        return testJson;
    }

    @RequestMapping("/dump-x-myheader")
    public String dumpHeaderValue(@RequestHeader("x-myheader") String xMyHeader) {
        return xMyHeader;
    }

    @RequestMapping("/dump-header")
    public Map<String, String> dumpHeaders(HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<String, String>();

        StringBuilder stringBuilder = new StringBuilder("Headers:");

        Enumeration headerNames = httpServletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

	public static void main(String[] args) {
		SpringApplication.run(CertsProviderApplication.class, args);

		Security.insertProviderAt(new CloudFoundryContainerProvider(), 2);

        //String s = new RestTemplate().getForObject("https://credhub.diego.security.cf-app.com:8844/api/v1/data>paths=true", String.class);

	}
}
