package com.green.fefu.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsScoreRes {
   private int stuId;

   private int year;

   private int semester;

   private int gradle;
}
