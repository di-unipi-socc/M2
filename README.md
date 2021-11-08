# Mining the Architecture of Microservice-based Applications
![](BirdEye.png)
### For Running M<sup>2</sup> miner:
```c++
$ cd target
```
and run the jar file by following command:
```java
$ java -jar  M2Miner-1.0.jar <inputFilesDirectory> [-i]
```
where "inputFileDirectory" is the complete path to the folder containing all the input files (i.e., Kubernetes/Istio manifests and graph data JSON file) for M<sup>2</sup>, and an optional parameter "-i" for enabling interactive node refinement.
  ### Reminder:
  Every Kubernetes manifest and Every Istio configuration must be in separated single files in order to get the correct output. The microTOSCA specification output file will be generated and stored in the same folder as input directory.
