package learn.kd.catalog.service;

import learn.kd.catalog.service.model.CatalogItem;
import learn.kd.catalog.service.model.Rating;

public interface CatalogInfo {

    public CatalogItem getCatalogItem(Rating rating);
    
    public void callErrorUri();
}
