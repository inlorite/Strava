package es.deusto.ingenieria.sd.auctions.server.data.dao;

import java.util.List;

//This interface defines the basic methods of Data Access Object Pattern
//This interface uses the concept of a "template". 
//Classes implementing it must specify the concrete type on which the methods are applied.
public interface IDataAccessObject<DomainObject> {
	public void store(DomainObject object);
	public void delete(DomainObject object);
	public List<DomainObject> findAll();
	public DomainObject find(String param);
}
