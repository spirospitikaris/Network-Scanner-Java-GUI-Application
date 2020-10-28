/*
Application name: NetStats Network Scanner
Copyright (c) 2020. All rights reserved.
This software has developed in the context of the course Computer Security, 
under the supervision of Professor Dr Aine MacDermott of Liverpool John Moores University.
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this 
file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under 
the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY 
KIND, either express or implied. See the License for the specific language governing 
permissions and limitations under the License.
Trademark Legal Notice All product names, trademarks and registered trademarks are 
property of their respective owners. All company, product and service names used in 
this software are for identification purposes only.
 */

import java.awt.EventQueue;

/**
 * This class launches the main application, starting with splashscreen window
 * @author Simonian Artour, Pulaczewski Bartosz, Ilias Stypas, Spyridon Pitikaris, Thomas Dowd 
 * @version 1.0
 */

public class Launcher {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	
                    Splashscreen splash = new Splashscreen(9000);
                    splash.showSplashScreen();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}