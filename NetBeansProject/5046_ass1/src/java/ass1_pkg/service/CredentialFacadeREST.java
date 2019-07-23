/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass1_pkg.service;

import ass1_pkg.Credential;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
/**
 *
 * @author 42901
 */
@Stateless
@Path("ass1_pkg.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "5046_ass1PU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credential find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

   /*
     @GET
        @Path("findByUserId/{userId}")
        @Produces({"application/json"})
        public List<Credential> findByUserId(@PathParam("userId") int userId) {
            Query query = em.createNamedQuery("Credential.findByUserId");
            query.setParameter("userId",userId);
            return query.getResultList();
        }
    */
    @GET
        @Path("findByPwHash/{pwHash}")
        @Produces({"application/json"})
        public List<Credential> findByPwHash(@PathParam("pwHash") String pwHash) {
            Query query = em.createNamedQuery("Credential.findByPwHash");
            query.setParameter("pwHash",pwHash);
            return query.getResultList();
        }
        
        @GET
        @Path("findBySignupDate/{signupDate}")
        @Produces({"application/json"})
        public List<Credential>findBySignupDate(@PathParam("signupDate") String signupDate) {
            Query query = em.createNamedQuery("Credential.findBySignupDate");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
              
        try {
            Date signup = format.parse ( signupDate );
             query.setParameter("signupDate",signup);
        } catch (ParseException ex) {
            Logger.getLogger(CredentialFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
           
            return query.getResultList();
        }
        
      
       @GET
       @Path("findByUserId/{userId}")
        @Produces({"application/json"})
        public List<Credential> findByUserId(@PathParam("userId") Integer userId) {
            TypedQuery<Credential> q = em.createQuery("SELECT s FROM Credential s WHERE s.userId.userId = :userId",Credential.class);
            q.setParameter("userId", userId);
            return q.getResultList();
}

    
}
