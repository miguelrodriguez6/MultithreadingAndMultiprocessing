# MultithreadingAndMultiprocessing - Execution Guide

This repository contains a Java program demonstrating communication between two players.

- **Single Process Execution**: Runs both players within a single Java process.
- **Multiple Process Execution**: Runs each player in a separate Java process.

## **Scripts Overview**

### **1. `run-single-process.sh`** (Single Process Execution)
This script compiles and executes the Java program using a single process. Both players run in the same JVM.

#### **How to Use**
```sh
chmod +x run-single-process.sh   # Give execution permission
./run-single-process.sh          # Run the script
```

### **2. `run-multiple-process.sh`** (Multiple Process Execution)
This script compiles and executes the Java program using **two separate processes**, ensuring each player runs with a different PID.

#### **How to Use**
```sh
chmod +x run-multiple-process.sh  # Give execution permission
./run-multiple-process.sh         # Run the script
```
