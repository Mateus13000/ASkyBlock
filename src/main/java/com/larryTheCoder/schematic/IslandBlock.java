  zx����J�@��]�/�^���.��L5��Yvc��y�Y�������L~yg�8�j�7�*ή�ow���<�꾹���\t�����CSv?՛�jX�cL�����]��Ήe���o�_��C�[u�]9)�Ya��9�%�i�D<�1�|��������01��e�j.�}۷ͮzY���>-�,ۛ�������ֻ��z>��U7��1��   �qL���eDL� H����p'����
 �|�1z�\  ���A   �����J=X� �  $�  �dԃ �^�ԃ �z �S  @2�A H�ԃ��A @= ɩ   �  �W���z P@r�A   HF= �z�H= � 9�    $���J=x� ԃ ��z   �Q@z�� �  $�  �dԃ �^�'�A @= ɩ   �  �W���z P@r�A   HF= �zp� ԃ ��z   �Q@z��� �  $�  �dԃ �^�Oԃ �z �S  @2�A H�ԃ��A @= ɩ   �  �W��3�  ���ԃ   ��z �+���  ��䪨>WU�/���n��u�������E�y\��"~N���                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    yx����J�@��]�/�^���.��L5��Yvc��y�Y�������I~yg�8�j�7�*ή�ow���<�꾹���\t����1�Д���Ͱ����sFq�q}Wm�sb�>4��<�m�м�V�vWN�rVcr�p�x.σqL/��m������h���ɲ]5����fW��M�YO�7��������o��n6,��q|�ꆷ�sL!�   Hb����_�x ҩ��2�	f���  �q�^*W H�z   ��� �RV�A @= ɩ   �  �W���  ���ԃ   ��z �+��z P@r�A   HF= �zp� ԃ ��z   �Q@z�<R �A HN=   ɨ �R� �  $�  �dԃ �^���A @= ɩ   �  �W���z P@r�A   HF= �zp� ԃ ��z   �Q@z��� �  $�  �dԃ �^���A @= ɩ   �  �W���  ���ԃ   ��z �+��z P@r�A   HF= �z�L= � 9�    $���J=�F= � �*��U��f�����f�l뾹�co�mW���_[8��                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   lic void setSkull(Map<String, Tag> map, int dataValue) {
        //skull = new SkullBlock();
        //skull.prep(map, dataValue);
    }

    public void setFlowerPot(Map<String, Tag> map) {
        pot = new PotBlock();
        pot.prep(map);
    }

    /**
     * Sets this block's sign data
     *
     * @param tileData
     */
    public void setSign(Map<String, Tag> tileData) {
        signText = new ArrayList<>();
        List<String> text = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String line = ((StringTag) tileData.get("Text" + String.valueOf(i))).getValue();
            // This value can actually be a string that says null sometimes.
            if (line.equalsIgnoreCase("null")) {
                line = "";
            }
            //System.out.println("DEBUG: line " + i + " = '"+ line + "' of length " + line.length());
            text.add(line);
        }

        JSONParser parser = new JSONParser();
        ContainerFactory containerFactory = new ContainerFactory() {
            @Override
            public List creatArrayContainer() {
                return new LinkedList<>();
            }

            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap<>();
            }

        };
        // This just removes all the JSON formatting and provides the raw text
        for (int line = 0; line < 4; line++) {
            String lineText = "";
            if (!text.get(line).equals("\"\"") && !text.get(line).isEmpty()) {
                //String lineText = text.get(line).replace("{\"extra\":[\"", "").replace("\"],\"text\":\"\"}", "");
                //Bukkit.getLogger().info("DEBUG: sign text = '" + text.get(line) + "'");
                if (text.get(line).startsWith("{")) {
                    // JSON string
                    try {

                        Map json = (Map) parser.parse(text.get(line), containerFactory);
                        List list = (List) json.get("extra");
                        //System.out.println("DEBUG1:" + JSONValue.toJSONString(list));
                        if (list != null) {
                            Iterator iter = list.iterator();
                            while (iter.hasNext()) {
                                Object next = iter.next();
                                String format = JSONValue.toJSONString(next);
                                //System.out.println("DEBUG2:" + format);
                                // This doesn't see right, but appears to be the easiest way to identify this string as JSON...
                                if (format.startsWith("{")) {
                                    // JSON string
                                    Map jsonFormat = (Map) parser.parse(format, containerFactory);
                                    Iterator formatIter = jsonFormat.entrySet().iterator();
                                    while (formatIter.hasNext()) {
                                        Map.Entry entry = (Map.Entry) formatIter.next();
                                        //System.out.println("DEBUG3:" + entry.getKey() + "=>" + entry.getValue());
                                        String key = entry.getKey().toString();
                                        String value = entry.getValue().toString();
                                        if (key.equalsIgnoreCase("color")) {
                                            try {
                                                lineText += TextFormat.valueOf(value.toUpperCase());
                                            } catch (Exception noColor) {
                                                Utils.ConsoleMsg("Unknown color " + value + " in sign when pasting schematic, skipping...");
                                            }
                                        } else if (key.equalsIgnoreCase("text")) {
                                            lineText += value;
                                        } else // Formatting - usually the value is always true, but check just in case
                                        {
                                            if (key.equalsIgnoreCase("obfuscated") && value.equalsIgnoreCase("true")) {
                                                lineText += TextFormat.OBFUSCATED;
                                            } else if (key.equalsIgnoreCase("underlined") && value.equalsIgnoreCase("true")) {
                                                lineText += TextFormat.UNDERLINE;
                                            } else {
                                                // The rest of the formats
                                                try {
                                                    lineText += TextFormat.valueOf(key.toUpperCase());
                                                } catch (Exception noFormat) {
                                                    // Ignore
                                                    //System.out.println("DEBUG3:" + key + "=>" + value);
                                                    Utils.ConsoleMsg("Unknown format " + value + " in sign when pasting schematic, skipping...");
                                                }
                                            }
                                        }
                                    }
                                } else// This is unformatted text. It is included in "". A reset is required to clear
                                // any previous formatting
                                 if (format.length() > 1) {
                                        lineText += TextFormat.RESET + format.substring(format.indexOf('"') + 1, format.lastIndexOf('"'));
                                    }
                            }
                        } else {
                            // No extra tag
                            json = (Map) parser.parse(text.get(line), containerFactory);
                            String value = (String) json.get("text");
                            //System.out.println("DEBUG text only?:" + value);
                            lineText += value;
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else // This is unformatted text (not JSON). It is included in "".
                {
                    if (text.get(line).length() > 1) {
                        try {
                            lineText = text.get(line).substring(text.get(line).indexOf('"') + 1, text.get(line).lastIndexOf('"'));
                        } catch (Exception e) {
                            //There may not be those "'s, so just use the raw line
                            lineText = text.get(line);
                        }
                    } else {
                        // just in case it isn't - show the raw line
                        lineText = text.get(line);
                    }
                }
                //Bukkit.getLogger().info("Line " + line + " is " + lineText);
            }
            signText.add(lineText);
        }
    }

    public void setBook(Map<String, Tag> tileData) {
        //Bukkit.getLogger().info("DEBUG: Book data ");
        Utils.ConsoleMsg(tileData.toString());
    }

    @SuppressWarnings("deprecation")
    public void setChest(Map<String, Tag> tileData) {
        try {
            ListTag chestItems = (ListTag) tileData.get("Items");
            if (chestItems != null) {
                //int number = 0;
                chestItems.getValue().stream().filter((item) -> (item instanceof CompoundTag)).forEach((item) -> {
                    try {
                        // Id is a number
                        short itemType = (short) ((CompoundTag) item).getValue().get("id").getValue();
                        short itemDamage = (short) ((CompoundTag) item).getValue().get("Damage").getValue();
                        byte itemAmount = (byte) ((CompoundTag) item).getValue().get("Count").getValue();
                        byte itemSlot = (byte) ((CompoundTag) item).getValue().get("Slot").getValue();
                        chestContents.put((int) itemSlot, Item.get(itemType, (int) itemDamage, itemAmount));

                    } catch (ClassCastException ex) {
                        // Id is a material
                        String itemType = (String) ((CompoundTag) item).getValue().get("id").getValue();
                        try {
                            // Get the material
                            if (itemType.startsWith("minecraft:")) {
                                String material = itemType.substring(10).toUpperCase();
                                // Special case for non-standard material names
                                int itemMaterial;

                                //Bukkit.getLogger().info("DEBUG: " + material);
                                if (WETOME.containsKey(material)) {
                                    itemMaterial = WETOME.get(material);
                                } else {
                                    itemMaterial = Item.fromString(material).getId();
                                }
                                byte itemAmount = (byte) ((CompoundTag) item).getValue().get("Count").getValue();
                                short itemDamage = (short) ((CompoundTag) item).getValue().get("Damage").getValue();
                                byte itemSlot = (byte) ((CompoundTag) item).getValue().get("Slot").getValue();
                                chestContents.put((int) itemSlot, Item.get(itemMaterial, (int) itemDamage, itemAmount));
                            }
                        } catch (Exception exx) {
                            Utils.ConsoleMsg("Could not parse item [" + itemType.substring(10).toUpperCase() + "] in schematic");
                            exx.printStackTrace();
                        }
                    }
                }); // Format for chest items is:
                // id = short value of item id
                // Damage = short value of item damage
                // Count = the number of items
                // Slot = the slot in the chest
                // inventory
            }
        } catch (Exception e) {
            Utils.ConsoleMsg("Could not parse schematic file item, skipping!");
            if (ASkyBlock.get().isDebug()) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Paste this block at blockLoc
     *
     * @param usePhysics
     * @param blockLoc
     */
    public void paste(Location blockLoc, boolean usePhysics) {
        Location loc = new Location(x, y, z, 0, 0, blockLoc.getLevel()).add(blockLoc);
        loadChunkAt(loc);
        // Only paste air if it is below the sea level and in the overworld
        Block block = loc.getLevelBlock();
        // found the problem why blocks didnt shows up
        blockLoc.getLevel().setBlock(block, Block.get(typeId, data), usePhysics, true);

        if (signText != null) {
            cn.nukkit.nbt.tag.CompoundTag nbt = new cn.nukkit.nbt.tag.CompoundTag()
                    .putString("id", BlockEntity.SIGN)
                    .putInt("x", (int) block.x)
                    .putInt("y", (int) block.y)
                    .putInt("z", (int) block.z)
                    .putString("Text1", signText.get(0))
                    .putString("Text2", signText.get(1))
                    .putString("Text3", signText.get(2))
                    .putString("Text4", signText.get(3));
            BlockEntity.createBlockEntity(BlockEntity.SIGN, blockLoc.getLevel().getChunk((int) block.x >> 4, (int) block.z >> 4), nbt);
            new BlockEntitySign(blockLoc.getLevel().getChunk((int) block.x >> 4, (int) block.z >> 4), nbt);
        } else if (pot != null) {
            pot.set(blockLoc, block);
        } else if (Block.get(typeId, data).getId() == Block.CHEST) {
            TaskManager.runTaskLater(new ChestPopulateTask(loc, chestContents), 10);
        }
    }

    /**
     * @return Vector for where this block is in the schematic
     */
    public Vector3 getVector() {
        return new Vector3(x, y, z);
    }
}
