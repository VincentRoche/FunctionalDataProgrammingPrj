package com.tp.spark.core
import com.tp.spark.utils.FlyingBus
import com.tp.spark.utils.FlyingBus.Bus
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.log4j.Logger
import org.apache.log4j.Level

object AnalyzeData extends App {

  val pathToFile = "../nuclear-flying-buses.json"

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)


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

  // loadData().collect().foreach(println)

  /* ***********************************************************************
  ************************************************************************
  **************************** ANALYSIS ********************************
  ********************************************************************
  ******************************************************************
   */

  // Is there more broken buses when the weather is hot or cold ?
  println(" -------- Is there more broken buses when the weather is hot or cold ? -------- ")
  loadData().filter(_.broken == true).groupBy(_.weather).mapValues(_.size).foreach(println)

  println("\n")

  // Is there more broken buses in the northern or the southern hemisphere ?
  println(" -------- Is there more broken buses in the northern hemisphere ? -------- ")
  loadData().filter(_.broken == true).groupBy(_.country.northHemisphere).mapValues(_.size).sortBy(_._1, false).foreach(println)

  println("\n")

  // Amoung the broken buses, how many are because of an empty fuel tank
  println(" -------- Amoung the broken buses, how many are because of an empty fuel tank ? -------- ")
  println(" => Number : " + loadData().filter(tuple => (tuple.broken == true && tuple.fuel == 0)).count())

  println("\n")

  // Which lines have more full buses ?
  println(" -------- Which lines have more full buses ? -------- ")
  loadData().filter(_.passengers == 0).groupBy(_.line).mapValues(_.size).sortBy(_._1, false).foreach(println)

  println("\n")




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