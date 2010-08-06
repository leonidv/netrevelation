/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vygovskiy

/**
 *
 * @author leonidv
 */
class DataProvider {
    def static trace(String address) {
        Process proc = "tracepath ${address}".execute()
        proc.inputStream
    }

    def static fromFile(String fileName) {
        new File(fileName).newInputStream()
    }

    def static nmap(String net) {
        Process proc = "nmap -n -sP -PE -PS22,25,80 -PA21,23,80,3389 ${net}".execute()
        proc.inputStream
    }
}

