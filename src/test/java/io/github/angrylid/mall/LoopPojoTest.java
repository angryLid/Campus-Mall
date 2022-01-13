package io.github.angrylid.mall;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import io.github.angrylid.mall.dto.PostProductDto;

public class LoopPojoTest {
    @Test
    void testReflect() throws IllegalAccessException {
        var dto = new PostProductDto();
        dto.setTitle("Title Content");
        dto.setDescription("DESC");
        dto.setPrice("PRICE");
        dto.setImage0(new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes()));

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().startsWith("image")) {
                f.setAccessible(true);
                Object image = f.get(dto);
                System.out.printf("%s: %s\n", f.getName(), image);
            }

        }
    }
}
