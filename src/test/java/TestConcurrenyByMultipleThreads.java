import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestConcurrenyByMultipleThreads {

    public static void main(String[] args) {
        A aa = new A();
        aa.start();
        C  cc = new C();
        cc.start();
    }


}

class  A extends Thread {

    @Override
    public void run(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://127.0.0.1:8080/atm/withdraw/1/50",
                String.class);
        responseEntity = restTemplate.getForEntity("http://127.0.0.1:8080/atm/withdraw/2/50",
                String.class);
    }
}

class  C extends Thread {

    @Override
    public void run(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header  = new HttpHeaders();

        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"accNum\":\"2\",\"actionAmount\":\"200\"}", header );
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://127.0.0.1:8080/atm/withdraw/1/50",
                String.class);
        responseEntity = restTemplate.getForEntity("http://127.0.0.1:8080/atm/withdraw/2/50",
                String.class);
    }
}
