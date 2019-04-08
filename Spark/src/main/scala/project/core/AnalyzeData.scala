package com.tp.spark.core
import com.tp.spark.utils.FlyingBus
import com.tp.spark.utils.FlyingBus.Bus
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object AnalyzeData extends App {

  val pathToFile = "../nuclear-flying-buses.json"

  /**
    *  Load the data from the json file and return an RDD of Bus
    */
  def loadData(): RDD[Bus] = {
    // create spark configuration and spark context
    val conf = new SparkConf()
      .setAppName("Flying Bus data mining")
      .setMaster("local[*]")

    val sc = SparkContext.getOrCreate(conf)

    sc.textFile(pathToFile)
      .mapPartitions(FlyingBus.parseFromJson(_))

  }

  /* Print all data */

  loadData().collect().foreach(println)

  /* ***********************************************************************
  ************************************************************************
  **************************** ANALYSIS ********************************
  ********************************************************************
  ******************************************************************
   */

  // print broken ones with hot and cold weather
  // loadData().flatMap{case t if (t.weather == "hot" || t.weather == "cold") && t.broken == true => Some(t) case t =>  None}.filter(_ != None).foreach(println)

  // Is there more broken buses when the weather is hot or cold ?
  //loadData().filter(tuple => tuple.weather == "weather" || tuple.weather == "hot").List.length()
  println(" -------- Is there more broken buses when the weather is hot or cold ? -------- ")
  loadData().flatMap{case t if (t.weather == "hot" || t.weather == "cold") && t.broken == true => Some(t) case t =>  None}.filter(_ != None).groupBy(_.weather).mapValues(_.size).foreach(println)

  // Is there more broken buses in the northern or the souther hemisphere ?
  println(" -------- Is there more broken buses when the weather is hot or cold ? -------- ")
  loadData().groupBy(_.country.northHemisphere).mapValues(_.size).foreach(println)

  // Amoung the broken buses, how many are because of an empty fuel tank
  println(" => Amoung the broken buses, how many are because of an empty fuel tank : " + loadData().filter(tuple => (tuple.broken == false && tuple.fuel == 0)).count())

  /* ***********************************************************************
  ************************************************************************
  **************************** SIMPLE QUERIES **************************
  ********************************************************************
  ******************************************************************
   */
/*
  /* Number of nuclear flying buses in the northern hemisphere */
  println("Number of nuclear flying buses in the northern hemisphere : " + loadData().filter(_.country.northHemisphere == false).count())

  /* Number of nuclear flying buses in the southern hemisphere */
  println("Number of nuclear flying buses in the southern hemisphere : " + loadData().filter(_.country.northHemisphere == true).count())

  println(" => List of nuclear flying buses in the northern hemisphere : ")
  loadData().groupBy(_.country.northHemisphere == false).foreach(println)

  /* Nuclear Flying buses sorted by kms */
  println(" -------- Nuclear flying buses sorted by kms --------- ")
  loadData().sortBy(_.totalKms, ascending = false).foreach(println)

*/

}