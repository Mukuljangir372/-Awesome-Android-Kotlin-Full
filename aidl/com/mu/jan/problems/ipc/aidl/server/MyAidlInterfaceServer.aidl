// MyAidlInterfaceServer.aidl
package com.mu.jan.problems.ipc.aidl.server;

// Declare any non-default types here with import statements

interface MyAidlInterfaceServer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   int sum(int n1,int n2);
}