import org.apache.spark.sql.{Row,SaveMode, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SparkConsoleApp extends App {

     val spark: SparkSession = SparkSession.builder()
    .master("local[1]")
    .appName("gruppe2")
    .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    var stopped = false
    println("hey there, using this program you can register users to the Spark database")
    var command = 0


    while(!stopped){
        println("type 1 to add a new user, 2 to view the users in the database, 3 to sort by income"
     + " and 4 to quit the program")
        command = readInt()
        if(command==1){
            val name = readLine("Enter the name of the user: ")
            val country = readLine("Enter the country of the user: ")
            print("Enter the yearly income of the user: ")
            val income = readInt()
            print("Enter the age of the user: ")
            val age = readInt()

   
            val schema = StructType(Array(
                StructField("name",StringType,true),
                StructField("country",StringType,true),
                StructField("income", IntegerType, true),
                StructField("age", IntegerType, true)

            ))
            val user = Seq(Row(name,country,income,age))
            val df = spark.createDataFrame(
            spark.sparkContext.parallelize(user),schema)

            df.write.mode("append").parquet("parquet/users.parquet")

            println("added user " + name)
            

        }else if(command==2){
            val dataframe = spark.read.format("parquet").load("parquet/users.parquet")
            dataframe.show()

        }else if(command==3){
            
             println("sorted by income")
            val dataframe = spark.read.format("parquet").load("parquet/users.parquet")


             val gdpTop = dataframe.sort(col("income").desc).show()
        
        }

        else if(command == 4){
            stopped = true
            println("quiting program....")
        }else{
            println("you typed a wrong command, try again")
           
        }
        
    }
    
}