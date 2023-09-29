package io.github.projektalmanac.amoxtli.backend.service.consume;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volumes;
import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBookService {
    private final Books.Volumes volumesService;

    public GoogleBookService() {
        try {
            this.volumesService = new Books.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                null
            )
            .setApplicationName("Amoxtli")
            .setGoogleClientRequestInitializer(new BooksRequestInitializer("TuClaveDeAPI"))
            .build()
            .volumes();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la API de Google Books");
        }
    }

    public List<VolumeInfo> searchVolumeInfo(List<Book> books) {
        List<VolumeInfo> volumeInfoList = new ArrayList<>();

        try {
            for (Book book : books) {
                String isbn = book.getIsbn(); 
                Books.Volumes.List volumesList = volumesService.list(isbn);
                Volumes volumes = volumesList.execute();

                if (volumes.getItems() != null && !volumes.getItems().isEmpty()) {
                    Volume volume = volumes.getItems().get(0);
                    VolumeInfo volumeInfo = volume.getVolumeInfo();
                    volumeInfoList.add(volumeInfo);
                }
            }
        } catch (Exception e) {
            throw new java.lang.RuntimeException("Error al buscar volúmenes en Google Books");
        }

        return volumeInfoList;
    }

    public VolumeInfo getVolumeInfoByIsbn(String isbn) {
        try {
            Books.Volumes.List volumesList = volumesService.list(isbn);
            Volumes volumes = volumesList.execute();
    
            if (volumes.getItems() == null || volumes.getItems().isEmpty()) {
                throw new ResourceNotFoundException("Libro con ISBN " + isbn + " no encontrado en Google Books.");
            }

            Volume volume = volumes.getItems().get(0);
            return volume.getVolumeInfo();
        } catch (IOException e) {
            throw new RuntimeException("Error al buscar volúmenes en Google Books");
        }
    }
    
}
