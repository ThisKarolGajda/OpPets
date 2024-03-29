package me.opkarol.oppets.api.graphic;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

public interface IGetter {
    GETTER_TYPE getGetterType();

    Object getObject();

    enum GETTER_TYPE {
        ADDON,
        PET,
        SHOP,
        ITEM_STACK,
        EGG_RECIPE
    }
}