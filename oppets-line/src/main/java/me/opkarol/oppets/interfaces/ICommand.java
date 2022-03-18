package me.opkarol.oppets.interfaces;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.command.CommandSender;

public interface ICommand {

    /**
     * Main method which takes sender and arguments, which then it analyzes and configures
     * what have been chosen before.
     *
     * @param sender command sender
     * @param args table of string
     * @return boolean if statement was successfully launched
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * @return string permission which is required to use specific command
     */
    String getPermission();

    /**
     * @return string sub command which is a tabComplete and command executor provider.
     */
    String getSubCommand();
}
