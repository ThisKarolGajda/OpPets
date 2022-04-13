package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.ConfigUtils;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * The type Ability.
 */
public class Ability {
    /**
     * The Ability.
     */
    private SkillEnums.SkillsAbilities ability;
    /**
     * The Command.
     */
    private String COMMAND;
    /**
     * The Connection.
     */
    private String CONNECTION;
    /**
     * The Effect.
     */
    private String EFFECT;
    /**
     * The Treasure.
     */
    private String TREASURE;
    /**
     * The Message.
     */
    private String MESSAGE;
    /**
     * The Plugin action.
     */
    private String pluginAction;


    /**
     * Instantiates a new Ability.
     *
     * @param ability the ability
     * @param iPath   the path
     */
    public Ability(SkillEnums.SkillsAbilities ability, String iPath) {
        setAbility(ability);
        FileConfiguration config = ConfigUtils.getConfig();
        switch (ability) {
            case CUSTOM_COMMAND:
                COMMAND = config.getString(iPath + "options.command");
                break;
            case PLUGIN_CONNECTION:
                CONNECTION = config.getString(iPath + "connection");
                pluginAction = config.getString(iPath + "options.action");
                break;
            case VANILLA_EFFECT:
                StringBuilder stringBuilder = new StringBuilder();
                iPath = iPath + "options.";
                stringBuilder.append(config.getString(iPath + "effect")).append(";").append(config.getString(iPath + "duration")).append(";").append(config.getString(iPath + "amplifier"));
                EFFECT = stringBuilder.toString();
                break;
            case TREASURE:
                TREASURE = "";
                //TODO: Add json support
                break;
            case CUSTOM_MESSAGE:
                MESSAGE = FormatUtils.formatMessage(config.getString(iPath + "message"));
                break;
        }
    }

    /**
     * Gets ability.
     *
     * @return the ability
     */
    public SkillEnums.SkillsAbilities getAbility() {
        return ability;
    }

    /**
     * Sets ability.
     *
     * @param ability the ability
     */
    public void setAbility(SkillEnums.SkillsAbilities ability) {
        this.ability = ability;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCOMMAND() {
        return COMMAND;
    }

    /**
     * Sets command.
     *
     * @param COMMAND the command
     */
    public void setCOMMAND(String COMMAND) {
        this.COMMAND = COMMAND;
    }

    /**
     * Gets plugin connection.
     *
     * @return the plugin connection
     */
    public String getPLUGIN_CONNECTION() {
        return CONNECTION;
    }

    /**
     * Gets vanilla effect.
     *
     * @return the vanilla effect
     */
    public String getVANILLA_EFFECT() {
        return EFFECT;
    }

    /**
     * Gets treasure.
     *
     * @return the treasure
     */
    public String getTREASURE() {
        return TREASURE;
    }

    /**
     * Sets treasure.
     *
     * @param TREASURE the treasure
     */
    public void setTREASURE(String TREASURE) {
        this.TREASURE = TREASURE;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public String getCONNECTION() {
        return CONNECTION;
    }

    /**
     * Sets connection.
     *
     * @param CONNECTION the connection
     */
    public void setCONNECTION(String CONNECTION) {
        this.CONNECTION = CONNECTION;
    }

    /**
     * Gets plugin action.
     *
     * @return the plugin action
     */
    public String getPluginAction() {
        return pluginAction;
    }

    /**
     * Sets plugin action.
     *
     * @param pluginAction the plugin action
     */
    public void setPluginAction(String pluginAction) {
        this.pluginAction = pluginAction;
    }

    /**
     * Sets effect.
     *
     * @param EFFECT the effect
     */
    public void setEFFECT(String EFFECT) {
        this.EFFECT = EFFECT;
    }

    /**
     * Gets effect.
     *
     * @return the effect
     */
    public String getEFFECT() {
        return EFFECT;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMESSAGE() {
        return MESSAGE;
    }

    /**
     * Sets message.
     *
     * @param MESSAGE the message
     */
    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
