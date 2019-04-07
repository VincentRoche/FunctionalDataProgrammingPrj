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

  loadData().collect().foreach(println)

}