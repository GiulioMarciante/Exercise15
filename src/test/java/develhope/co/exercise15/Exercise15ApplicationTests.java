package develhope.co.exercise15;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class Exercise15ApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void testCreateUser() throws Exception {
		Users users = new Users(1l,"Luigi","Neri","luigineri@hotmail.com");
		mockMvc.perform(post("/api/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(users)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Luigi"))
				.andExpect(jsonPath("$.surname").value("Neri"))
				.andExpect(jsonPath("$.email").value("luigineri@hotmail.com"));
	}
	@Test
	public void testGetUser() throws Exception{
		Users users = new Users(1l,"Luigi","Neri","luigineri@hotmail.com");
		userRepository.save(users);
		mockMvc.perform(get("/api/get/{id}",users.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(users.getId()))
				.andExpect(jsonPath("$.name").value("Luigi"))
				.andExpect(jsonPath("$.surname").value("Neri"))
				.andExpect(jsonPath("$.email").value("luigineri@hotmail.com"));
	}
	@Test
	public void testPutUser()throws Exception{
		Users users = new Users(1l,"Luigi","Neri","luigineri@hotmail.com");
		userRepository.save(users);
		Users updateUsers = new Users(1l,"Mario","Rossi","mariorossi@hotmail.com");
		mockMvc.perform(put("/api/put/{id}",users.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateUsers)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(users.getId()))
				.andExpect(jsonPath("$.name").value("Mario"))
				.andExpect(jsonPath("$.surname").value("Rossi"))
				.andExpect(jsonPath("$.email").value("mariorossi@hotmail.com"));
	}
	@Test
	public void testDeleteById()throws Exception{
		Users users = new Users(1l,"Luigi","Neri","luigineri@hotmail.com");
		userRepository.save(users);
		mockMvc.perform(delete("/api/delete/{id}",users.getId()))
				.andExpect(status().isOk());
	}
}
