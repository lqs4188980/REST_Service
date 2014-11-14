package com.qishen.RESTService.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.owlike.genson.GensonBuilder;
import com.qishen.RESTService.model.Scientist;


public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Originally, there is nothing");
		JerseyClient client = JerseyClientBuilder.createClient();
		System.out.println(client.target("http://54.197.77.33:8080/REST_Service")
			  .path("api").path("objects")
			  .request(MediaType.APPLICATION_JSON)
			  .buildGet()
			  .invoke().readEntity(String.class));
		System.out.println("***************************************************");
		
		// Put Test
		System.out.println("PUT a new Object");
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target("http://54.197.77.33:8080/REST_Service");
		Scientist data = new Scientist();
		data.setFirstName("Jim");
		data.setLastName("Lawrence");
		data.setDateOfBirth("1904-06-20");
		data.setDateOfDeath("1990-08-09");
		
		System.out.println("I PUT the same object twice, and return the same one");
		for (int i = 0; i < 2; i++) {
			System.out.println(t.path("api").path("objects").path("26")
					.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.put(Entity.entity(new GensonBuilder().create().serialize(data), MediaType.APPLICATION_JSON), String.class));
		}
		
		System.out.println("***************************************************");
		// Get Obj Test
		System.out.println("Get the object we just put");
		System.out.println(t.path("api").path("objects").path("26")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
		
		System.out.println("***************************************************");
		// Post obj Test
		System.out.println("We use POST to create a new object on the server");
		String s = "{\"dateOfBirth\":\"1904-06-07\",\"dateOfDeath\":\"1990-08-09\",\"firstName\":\"Jim\",\"lastName\":\"Lawrence\",\"uid\":\"c6042e7d-0129-4224-8c53-170dacb0b57b\"}";
		System.out.println("I POST the same object twice, and return two different obj");
		for (int i = 0; i < 2; i++) {
			System.out.println(t.path("api").path("objects")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(s, MediaType.APPLICATION_JSON), String.class));
		}
		
		System.out.println("***************************************************");
		// Get List Test
		System.out.println("Now we check the url list");
		System.out.println(t.path("api").path("objects")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
		
		System.out.println("***************************************************");
		// Delete Test
		System.out.println("Then we delete an obj which the id is 26");
		t.path("api").path("objects").path("26")
		.request(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.delete(String.class);
		
		System.out.println(t.path("api").path("objects")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
	}

}
