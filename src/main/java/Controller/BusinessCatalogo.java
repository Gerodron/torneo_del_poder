package Controller;

import Model.Catalogo.Catalogo_BDO;
import Model.Catalogo.Catalogo_DAO;

import java.util.List;

public class BusinessCatalogo {
    public List<Catalogo_BDO> getCatalogos(String nombre){
        try{
            Catalogo_DAO dao = new Catalogo_DAO();
            return  dao.getCatalogos(nombre);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
