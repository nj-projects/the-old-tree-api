package me.nolanjames.theoldtreeapi.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nolanjames.theoldtreeapi.category.dto.CategoryRequest;
import me.nolanjames.theoldtreeapi.category.dto.CategoryResponse;
import me.nolanjames.theoldtreeapi.category.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CategoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc()
class CategoryControllerTest {
    private CategoryRequest request;
    private CategoryResponse response;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        String publicId = "123456";

        request = new CategoryRequest(
                "Service Test Name",
                "Service Test Description"
        );

        response = new CategoryResponse(
                "Service Test Name",
                "Service Test Description",
                publicId
        );
    }

    @Test
    void createCategory() throws Exception {
        Mockito.when(categoryService.createCategory(ArgumentMatchers.any())).thenReturn(response);
        String json = mapper.writeValueAsString(response);

        mvc.perform(post("/category").with(csrf()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(request.name())))
                .andExpect(jsonPath("$.description", Matchers.equalTo(request.description())));
    }

    @Test
    void listCategories() throws Exception {
        Mockito.when(categoryService.listCategories()).thenReturn(List.of(response));
        String json = mapper.writeValueAsString(response);

        mvc.perform(get("/category").with(csrf()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getCategoryByName() {
        Mockito.when(categoryService.findByName(ArgumentMatchers.matches(request.name()))).thenReturn(response);

//        mvc.perform(get("/category").param("name", request.name()).with(csrf()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
//                .andDo(print());
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(request.name()));

    }
}