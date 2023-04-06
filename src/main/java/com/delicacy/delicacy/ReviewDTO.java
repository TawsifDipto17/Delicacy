package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ReviewDTO extends ArrayList<ReviewDTO> {

    private String colItemId;

    private String colName;


    private String colPrice;

    private Double colRating;

    private Button colReview;

    private String comment;



    public ReviewDTO (String id ,String name,String price, Double rating, String message) {
        colItemId=id;
        colName=name;
        colPrice=price;
        colRating=rating;
        comment=message;
        colReview=new Button("Click Here");
        {
            colReview.setOnAction((ActionEvent event) -> {
               ReviewNRatingController R = new ReviewNRatingController();

               R.getRevData(colItemId);
            });
        };
    }


    public String getColItemId()
    {

        return colItemId;
    }

    public void setColItemId(String id)
    {
        colItemId=id;
    }


    public String getColName()
    {
        return colName;
    }

    public void setColName(String name) {
        colName = name;
    }



    public String getColPrice()
    {
        return colPrice;
    }

    public void setColPrice(String price){colPrice = price ;}

    public Double getColRating()
    {
        return colRating;
    }

    public void setColRating(Double rating)
    {
        colRating=rating;
    }

    public Button getColReview()
    {

        return colReview;
    }

    public void setColReview(Button review)
    {

        colReview=review;


    }
    public String getComment()
    {
        return comment;
    }

    public void setComment(String message)
    {
        comment=message;
    }




    @Override
    public Stream<ReviewDTO> stream() {
        return null;
    }
}
