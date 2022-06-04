package me.opkarol.oppets.skills.types;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.skills.SkillEnums;
import me.opkarol.oppets.utils.external.ConfigUtils;
import me.opkarol.oppets.utils.external.FormatUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class Ability {
    private SkillEnums.SkillsAbilities ability;
    private String COMMAND;
    private String CONNECTION;
    private String EFFECT;
    private String TREASURE;
    private String MESSAGE;
    private String pluginAction;


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

    public SkillEnums.SkillsAbilities getAbility() {
        return ability;
    }

    public void setAbility(SkillEnums.SkillsAbilities ability) {
        this.ability = ability;
    }

    public String getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(String COMMAND) {
        this.COMMAND = COMMAND;
    }

    public String getPLUGIN_CONNECTION() {
        return CONNECTION;
    }

    public String getVANILLA_EFFECT() {
        return EFFECT;
    }

    public String getTREASURE() {
        return TREASURE;
    }

    public void setTREASURE(String TREASURE) {
        this.TREASURE = TREASURE;
    }

    public String getCONNECTION() {
        return CONNECTION;
    }

    public void setCONNECTION(String CONNECTION) {
        this.CONNECTION = CONNECTION;
    }

    public String getPluginAction() {
        return pluginAction;
    }

    public void setPluginAction(String pluginAction) {
        this.pluginAction = pluginAction;
    }

    public void setEFFECT(String EFFECT) {
        this.EFFECT = EFFECT;
    }

    public String getEFFECT() {
        return EFFECT;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
