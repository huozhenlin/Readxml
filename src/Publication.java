
public class Publication {    
    private String id;    
    private String authors;    
  
    public String getAuthors() {  
        return authors;  
    }  
    public void setAuthors(String authors) {  
        this.authors = authors;  
    }  
    public String getId() {    
        return id;    
    }    
    public void setId(String string) {    
        this.id = string;    
    }    
      
    @Override    
    public String toString(){    
        return this.id+":"+this.authors;    
    }    



}
