package com.qishen.RESTService.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.qishen.RESTService.exception.ErrorMessage;
import com.qishen.RESTService.model.DAO;
import com.qishen.RESTService.model.Scientist;


// access path: "api/objects"
@Path("/objects")
public class ResourceHandler {
	// Data Access Object
	DAO instance = DAO.getInstance();
	
	@Context
	UriInfo uri;
	
	// access path: "api/objects/{id}"
	/*
	 * Update Object
	 */
	
	@PUT
	@Path("{id}")
	@Consumes("application/json")
	public Response update(@PathParam("id") String id, InputStream data) {
		Scientist s = instance.getObj(id);
		Genson gensonParser = new GensonBuilder()
								.rename("dob", "dateOfBirth")
								.rename("dod", "dateOfDeath").create();
		Scientist info = gensonParser.deserialize(data, Scientist.class);
		
		if (s != null) {
			s.setFirstName(info.getFirstName());
			s.setLastName(info.getLastName());
			s.setDateOfBirth(info.getDateOfBirth());
			s.setDateOfDeath(info.getDateOfDeath());
		} else {
			s = new Scientist();
			s.setUID(id);
			s.setFirstName(info.getFirstName());
			s.setLastName(info.getLastName());
			s.setDateOfBirth(info.getDateOfBirth());
			s.setDateOfDeath(info.getDateOfDeath());
			instance.addObj(s);
		}
		
		Genson genson = new GensonBuilder()
						.rename("dateOfBirth", "dob")
						.rename("dateOfDeath", "dod")
						.rename("UID", "uid").create();
		return Response.created(uri.getAbsolutePath()).entity(genson.serialize(s)).build();
	}
	
	
	// Access path: "api/objects/{id}"
	/*
	 * Read an object
	 */
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") String id) {
		Scientist s = instance.getObj(id);
		
		if (s != null) {
			return Response.created(uri.getAbsolutePath()).entity(getGensonForResponse().serialize(s)).build();
		}
		
		return Response.created(uri.getAbsolutePath()).entity(new GensonBuilder().create().serialize(new ErrorMessage("GET", uri.getAbsolutePath().toString(), "Object not exist"))).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAll() {
		Set<String> idSet = instance.getUIDSet();
		
		// This is a waste of space. We can build another model or use another json object.
		List<Map<String, String>> urlList = new ArrayList<Map<String, String>>();
		for (String s : idSet) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("url", "api/objects/" + s);
			urlList.add(m);
		}
		
		
		return Response.status(200).entity(new GensonBuilder().create().serialize(urlList)).build();
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(InputStream is) {
		Scientist s = new GensonBuilder().create().deserialize(is, Scientist.class);
		Scientist obj = new Scientist();
		
		obj.setFirstName(s.getFirstName());
		obj.setLastName(s.getLastName());
		obj.setDateOfBirth(s.getDateOfBirth());
		obj.setDateOfDeath(s.getDateOfDeath());
		
		instance.addObj(obj);
		
		return Response.created(uri.getAbsolutePath()).entity(getGensonForResponse().serialize(obj)).build();

	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		if (!instance.deleteObj(id)) {
			return Response.created(uri.getAbsolutePath())
						   .entity(new GensonBuilder().create().serialize(new ErrorMessage("DELETE", uri.getAbsolutePath().toString(), "Fail to delete non-exist object"))).build();
		}
		
		return Response.noContent().build();
	}
	
	private Genson getGensonForResponse() {
		return new GensonBuilder()
		.rename("dateOfBirth", "dob")
		.rename("dateOfDeath", "dod")
		.rename("UID", "uid").create();
	}
	

}
