package hr.fer.zemris.java.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import org.json.JSONArray;

import hr.fer.zemris.java.galerija.utility.GalerijaUtil;

import java.io.IOException;

import javax.servlet.ServletContext;

/**
 * REST class used for sending all tags, all names of images with specific tag
 * and all information of specific image.
 * 
 * @author Jelić, Nikola
 */
@Path("/tags")
public class TagJSON {

	@Context
	ServletContext context;

	/**
	 * Returns all tags.
	 * 
	 * @return all tags.
	 */
	@GET
	@Produces("application/json")
	public Response getAllTags() {
		try {
			JSONArray tagList = new JSONArray();
			for (String t : GalerijaUtil.tagsList(context)) {
				tagList.put(t);
			}

			return Response.status(Status.OK).entity(tagList.toString()).build();
		} catch (IOException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/**
	 * Returns list of images with specific tag. It is used as image src in html.
	 * 
	 * @param str name of tag.
	 * @return list of images with specific tag.
	 */
	@Path("{str}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageListByTag(@PathParam("str") String str) {
		try {
			JSONArray imageList = new JSONArray();
			for (String t : GalerijaUtil.imagesList(context, str)) {
				imageList.put(t);
			}

			return Response.status(Status.OK).entity(imageList.toString()).build();
		} catch (IOException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/**
	 * Returns informations about specific image.
	 * 
	 * @param name of image.
	 * @return list of image information.
	 */
	@Path("img/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfo(@PathParam("name") String name) {
		try {
			JSONArray imageList = new JSONArray();
			for (String t : GalerijaUtil.infoList(context, name)) {
				imageList.put(t);
			}

			return Response.status(Status.OK).entity(imageList.toString()).build();
		} catch (IOException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
