package ru.nino.mybar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.nino.mybar.service.InstrumentServiceImpl;

@ActiveProfiles("test")
@SpringBootTest
class MyBarApplicationTests {

    @Autowired
    InstrumentServiceImpl instrumentService;


    @Test
    void contextLoads() {
        Assertions.assertNotNull(instrumentService);
    }

}
