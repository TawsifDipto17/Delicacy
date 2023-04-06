package com.delicacy.delicacy;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ItemDTO extends ArrayList<ItemDTO> {
    private String itemCode;


    private String foodName;
    private Blob imageBlob;


    private String Description;

    private String Price;
    private ImageView imageView;





    public ItemDTO(String itemCode, String food, String unitPrice, String description, Blob image) {
        this.itemCode = itemCode;

        Description = description;

        Price = unitPrice;
        foodName=food;
        imageBlob=image;



    }


    public String getItemCode()
    {

        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }


    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public String getPrice()
    {
        return Price;
    }

    public void setPrice(String unitPrice)
    {
        Price = unitPrice;
    }

    public String getFoodName()
    {
        return foodName;
    }

    public void setFoodName(String food)
    {
       foodName = food;
    }

    public ImageView getImageView() throws FileNotFoundException {
        try {

        InputStream imageFile=imageBlob.getBinaryStream();

        Image im = new Image(imageFile);

        imageView=new ImageView(im);

        imageView.setFitWidth(250);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return imageView;
    }

    public void setImageView() throws FileNotFoundException {

        try {
            InputStream imageFile=imageBlob.getBinaryStream();

            Image im = new Image(imageFile);

            imageView=new ImageView(im);

            imageView.setFitWidth(200);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public Blob getImageBlob()
    {
        return imageBlob;
    }
    public void setImageBlob(Blob B)
    {
        imageBlob=B;
    }

    @Override
    public Stream<ItemDTO> stream() {
        return null;
    }
}
