package app.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.model.entities.Picture;

public interface PictureDao extends PagingAndSortingRepository<Picture, Long> {

}
