/*
 * Copyright 2013 Jesper Terkelsen.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
package dk.deck.aws.price.api.model;

/**
 *
 * @author Jesper Terkelsen
 */
public enum PricePeriod {
    HOURLY, MONTHLY;

    public String getDisplayString() {
        switch (this){
            case HOURLY: {
                return "/hour";
            }
            case MONTHLY : {
                return "/month";
            }
            default : {
                throw new IllegalStateException("Period not supported: " + this);
            }
        }
    }
    
    
}
