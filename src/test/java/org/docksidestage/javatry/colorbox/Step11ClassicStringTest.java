/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.util.*;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.bizfw.colorbox.impl.StandardColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, not using Stream API. <br>
 * Show answer by log() for question of javadoc. <br>
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author yuta.doi
 */
public class Step11ClassicStringTest extends PlainTestCase {

    public void test_show_setContents() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content != null) {
                    System.out.println("===");
                    System.out.println(content);
                    System.out.println("クラス名：" + content.getClass());
                }
            }

        }
    }

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * How many lengths does color name of first color-boxes have? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox colorBox = colorBoxList.get(0);
        BoxColor boxColor = colorBox.getColor();
        String colorName = boxColor.getColorName();
        int answer = colorName.length();
        log(answer, colorName); // also show name for visual check
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    int currentLength = ((String) content).length();
                    if (maxStr == null || maxStr.length() < currentLength) {
                        maxStr = strContent;
                    }
                }
            }
        }
        log(maxStr != null ? maxStr : "*Not found String content");
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String maxStr = null;
        String minStr = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    int currentLength = ((String) content).length();
                    if (maxStr == null || maxStr.length() < currentLength) {
                        maxStr = strContent;
                    }
                    if (minStr == null || minStr.length() > currentLength) {
                        minStr = strContent;
                    }
                }
            }
        }
        log(maxStr != null ? maxStr : "*Not found String content");
        log(minStr != null ? minStr : "*Not found String content");
        assert maxStr != null;
        log(maxStr.length() - minStr.length());
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {

        List<String> content_string_list = get_content_string_list();
        Collections.reverse(content_string_list);

        String str_max = find_max(content_string_list);

        content_string_list.remove(str_max);
        String str_second_max = find_max(content_string_list);

        log(str_max.length() + ": " + str_max);
        log(str_second_max.length() + ": " +str_second_max);
        log_answer(str_second_max);
    }

    private List<String> get_content_string_list() {
        List<String> str_list = new ArrayList<>();
        List<Object> content_list = get_content_list();

        for (Object content : content_list) {
            if (content != null) {
                String contentStr = content.toString();
                str_list.add(contentStr);
            }
        }
        return str_list;
    }

    private String find_max(List<String> str_list){
        String max_content = null;

        for (String contentStr : str_list) {
            if (max_content == null || max_content.length() < contentStr.length()) {
                max_content = contentStr;
            }
        }
        return max_content;
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int sum_length = 0; // 最終的な答えが0なのか、バグで、回らなかった場合でも、答えが0になってしまう。
        // ここを、nullにして、格納する際に初めて、数値を追加する。
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    sum_length += ((String) content).length();
                }
            }
        }
        log_answer(sum_length);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        BoxColor boxColor = null; // ここで、文字列を保存する方がスマート
        if (colorBoxList.isEmpty()) {
            log("あなたの部屋に、カラーボックスがありません。");
            return;
        }

        for (ColorBox colorBox : colorBoxList) {
            BoxColor color = colorBox.getColor();
            int length = color.getColorName().length();
            if (boxColor == null || boxColor.getColorName().length() < length) {
                boxColor = color;
            }
        }

        log_answer(boxColor.getColorName());
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("あなたの部屋に、カラーボックスがありません。");
            return;
        }

        ColorBox box_in_water = null; // ここをリストで作るという人もいる。これは問題文の読み方
        String content_water = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentStr = (String) content;
                    if (contentStr.startsWith("Water")){
                        box_in_water = colorBox;
                        content_water = contentStr;
                        break;
                    }
                }
            }
        }

        log(content_water);
        log_answer(box_in_water != null ? box_in_water.getColor().getColorName() : "*Not found box_in_water");
    }



    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        String target_str = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("あなたの部屋に、カラーボックスがありません。");
            return;
        }

        ColorBox front_colorBox = null;
        String content_end_front = null;
        outer_loop:for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                if (content instanceof String) {
                    String contentStr = (String) content;
                    if (contentStr.endsWith(target_str)) {
                        front_colorBox = colorBox;
                        content_end_front = contentStr;
                        break outer_loop;
                    }
                }
            }
        }
        // outer_loopは、forにタグを付けて、二重化されているループを一気に飛ばすことができる。

        if (front_colorBox == null) {
            log( "{}で終わる文字列は存在しません", target_str); // logから、formatを使うことができる。
            return;
        }
        log(content_end_front);
        log_answer(front_colorBox.getColor().getColorName());
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        String target_str = "front";
        String searched_content = get_str_endWith(target_str);
        if (searched_content == null) {
            log("{} で終わる文字列は存在しません", target_str);
            return;
        }

        int target_start_point = searched_content.indexOf(target_str)+1;
        log(searched_content);
        log_answer(target_start_point);
    }

    private String get_str_endWith(String end_str) {

        List<Object> content_list = get_content_list();
        for (Object content : content_list) {
            if (content instanceof String) {
                String contentStr = (String) content;
                if (contentStr.endsWith(end_str)) {
                    return contentStr;
                }
            }
        }
        return null;
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        String search_target = "ど";
        List<Object> content_list = get_content_list();
        for (Object content : content_list) {
            if (content instanceof String){
                int start_point_target_str = ((String) content).indexOf(search_target);
                if (start_point_target_str == -1) continue;

                int end_point_target_str = ((String) content).lastIndexOf(search_target);
                if (end_point_target_str != -1 && start_point_target_str != end_point_target_str){
                    log(content);
                    log_answer(end_point_target_str+1);
                    return;
                }
            }

        }
        log("{}を二つ以上含む文字列は存在しません", search_target);
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        String target_str = "front";
        String searched_str = get_str_endWith(target_str);
        if (searched_str == null) {
            log( "{}で終わる文字列は存在しません", target_str);
            return;
        }

        log(searched_str);

        String first_char = searched_str.substring(0, 1);
        log_answer(first_char);
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        String target_str = "Water";
        String str_startWith = get_str_startWith(target_str);
        if (str_startWith == null) {
            log(target_str + " で始まる文字列は存在しません");
            return;
        }

        String last_char = str_startWith.substring(str_startWith.length() - 1);
        log(str_startWith);
        log_answer(last_char);
    }

    private String get_str_startWith(String start_str) {
        List<Object> content_list = get_content_list();
        for (Object content : content_list) {
            if (content instanceof String) {
                String contentStr = (String) content;
                if (contentStr.startsWith(start_str)) {
                    return contentStr;
                }
            }
        }
        return null;
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        String target_str = "o";
        String str_containing_target = null;
        List<String> strings = get_strContent_list();

        for (String string : strings) {
            if (string.contains(target_str)) {
                str_containing_target = string;
                break;
            }
        }
        if (str_containing_target == null) {
            log(target_str + " を含んだ文字列は存在しません");
            return;
        }

        String str_replaced = str_containing_target.replace(target_str, "");
        log(str_containing_target);
        log(str_replaced);
        log_answer(str_replaced.length());

    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        String linux_file_separator = "/";
        String win_file_separator = "\\";

        List<java.io.File> files = get_fileContent_list();

        if (files.isEmpty()) {
            log("java.io.File型のコンテンツは存在しません");
            return;
        }

        String file_str = files.get(0).getPath();
        String replaced_file_str = file_str.replace(linux_file_separator, win_file_separator);
        log(file_str);
        log_answer(replaced_file_str);
    }



    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<Object> content_list = get_content_list();
        List<YourPrivateRoom.DevilBox> devilBoxes = new ArrayList<>();
        for (Object content : content_list) {
            if (content instanceof YourPrivateRoom.DevilBox) {
                devilBoxes.add((YourPrivateRoom.DevilBox) content);
            }
        }

        int sum_length_of_all_devil = 0;
        for (YourPrivateRoom.DevilBox devilBox : devilBoxes) {
            devilBox.wakeUp();
            devilBox.allowMe();
            devilBox.open();

            String devils_text;
            try {
                devils_text = devilBox.getText();
                // sum_length_of_all_devil += devils_text.length();
            } catch (YourPrivateRoom.DevilBoxTextNotFoundException e) {
                devils_text = "";
                // ここでLogを吐き出すようにするのもあり
                // 状況を知らせているだけの例外
                // 無視したことを伝えるためにこのように表記することがある。
                // -> catch (YourPrivateRoom.DevilBoxTextNotFoundException ignored) {}
            }
            sum_length_of_all_devil += devils_text.length();
            log(devils_text);
        }

        log_answer(sum_length_of_all_devil);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<Map> mapContent_list = get_mapContent_list();

        for (Map map : mapContent_list) {
            StringBuilder show_map = new StringBuilder("map:{ ");
            for (Object entry : map.entrySet()) {
                show_map.append(entry.toString()).append(" ; ");
            }
            show_map.append(" }");
            log(show_map);
        }
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<Map> mapContent_list = get_mapContent_list();

        for (Map map : mapContent_list) {
            log(convert_to_str_from_map(map));
        }
    }

    private String convert_to_str_from_map(java.util.Map map){
        StringBuilder show_map = new StringBuilder("map:{ ");

        boolean multiple_flag = false;
        for (Object key : map.keySet()) {
            if (multiple_flag) show_map.append(" ; "); // 最後の、セミコロンは入れない

            Object value = map.get(key);
            String valueStr;
            if (value instanceof Map) {
                Map valueMap = (Map) value;
                valueStr = convert_to_str_from_map(valueMap);
            }else{
                valueStr = value.toString();
            }
            show_map.append(key.toString()).append(" = ").append(valueStr);
            multiple_flag = true;
        }
        return show_map.append(" }").toString();
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals("white")) {
                BoxSpace upperSpace = ((StandardColorBox) colorBox).getUpperSpace();
                Object content = upperSpace.getContent();
                if (content instanceof YourPrivateRoom.SecretBox) {
                    String text = ((YourPrivateRoom.SecretBox) content).getText();
                    log(text);
                    Map map = convert_to_map_from_text(text);
                    log_answer(map.toString());
                }
            }
        }
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            if (colorBox.getColor().getColorName().equals("white")) {
                ArrayList<BoxSpace> spaceList = new ArrayList<>();
                spaceList.add(((StandardColorBox) colorBox).getMiddleSpace());
                spaceList.add(((StandardColorBox) colorBox).getLowerSpace());

                for (BoxSpace space : spaceList) {
                    Object content = space.getContent();
                    if (content instanceof YourPrivateRoom.SecretBox) {
                        String text = ((YourPrivateRoom.SecretBox) content).getText();
                        log(text);
                        Map map = convert_to_map_from_text(text);
                        log_answer(map.toString());
                    }
                }

            }
        }
    }

    public void test_convert_to_map_from_text() {
        String test_text_1 = "map:{ dockside = over ; hangar = map:{ mystic = performance ; shadow = musical } ; broadway = bbb }";
        Map map_1 = convert_to_map_from_text(test_text_1);
        log(("over".equals(map_1.get("dockside").toString())));
        Map hangarMap = (Map) map_1.get("hangar");
        log(("performance".equals(hangarMap.get("mystic").toString())));
        log(("musical".equals(hangarMap.get("shadow").toString())));
        log(("bbb".equals(map_1.get("broadway").toString())));

        String test_text_2 = "map:{ sea = map:{ dockside = [over, table, hello] ; hanger = [mystic, shadow, mirage] ; harbor = map:{ spring = fashion ; summer = pirates ; autumn = vi ; winter = jazz } } ; land = map:{ orleans = [oh, party] ; showbase = [oneman] } }";
        log(convert_to_map_from_text(test_text_2));

        String test_text_3 = "map:{ sea = map:{ dockside = [over, table, hello] } ; land = map:{ orleans = [oh, party]} }";
        log(convert_to_map_from_text(test_text_3));
    }


    public void test_map_converter() {
        List<Map> mapContent_list = get_mapContent_list();
        for (Map map : mapContent_list) {
            String mapStr = convert_to_str_from_map(map);
            Map mapRe = convert_to_map_from_text(mapStr);
            log(map.toString());
            log(compare_map(map, mapRe));
            log(compare_map(mapRe, map));
        }
    }

    private boolean compare_map(Map left, Map right) {
        for (Object key : left.keySet()) {
            Object valueLeft = left.get(key.toString());
            Object valueRight = right.get(key.toString());
            if (valueLeft instanceof Map && valueRight instanceof Map) {
                Map valueLeftMap = (Map) valueLeft;
                Map valueRightMap = ((Map) valueRight);
                if (!compare_map(valueLeftMap, valueRightMap)) {
                    throw new IllegalStateException("異なるMapです");
                }
            }
            else{
                if (!valueLeft.toString().equals(valueRight.toString())) {
                    throw new IllegalStateException("異なるMapです");
                }
            }
        }
        return true;
    }

    private Map convert_to_map_from_text(String text) {

        if (!text.startsWith("map:{") || !text.endsWith("}")) {
            throw new IllegalStateException("mapを表現しているテキストになっていません");
        }
        int startPos = 5;
        String remainText = text.substring(startPos).trim();

        Map<Object, Object> map = new LinkedHashMap<>();
        while (remainText.length() > 0){

            int equalPos = remainText.indexOf("=");
            String key_text = remainText.substring(0, equalPos).trim();
            remainText = remainText.substring(equalPos + 1).trim();

            Object value;
            int next_semicolon_index;

            if (remainText.startsWith("map:{")) {
                int range = range_kakko(remainText);
                String mapText = remainText.substring(0, range).trim();

                value = convert_to_map_from_text(mapText);

                remainText = remainText.substring(range);
                next_semicolon_index = remainText.indexOf(";");
                if (next_semicolon_index == -1) {
                    next_semicolon_index = remainText.indexOf("}");
                }
            } else {
                next_semicolon_index = remainText.indexOf(";");
                if (next_semicolon_index == -1) {
                    next_semicolon_index = remainText.indexOf("}");
                }
                value = remainText.substring(0, next_semicolon_index).trim();
            }

            remainText = remainText.substring(next_semicolon_index + 1);
            map.put(key_text, value);
        }

        return map;
    }




    public void test_count_kakko(){
        String test_text_1 = "map:{ sea = map:{ dockside = [over, table, hello] } ; land = map:{ orleans = [oh, party]} }";
        int count_1 = range_kakko(test_text_1);
        log(count_1);
        log(test_text_1.substring(0, count_1));


        String test_text_2 = "map:{ dockside = [over, table, hello] } ; land = map:{ orleans = [oh, party]} }";
        int count_2 = range_kakko(test_text_2);
        log(count_2);
        log(test_text_2.substring(0, count_2));

    }

    private int range_kakko(String str){
        int current = str.indexOf("{")+1;
        int count = 1;
        // String strRemain = str.substring(current);
        while(count > 0){
            int start_kakko = str.indexOf("{", current);
            int finish_kakko = str.indexOf("}", current);
            if (start_kakko < finish_kakko && start_kakko != -1) {
                count += 1;
                current = start_kakko+1;
            }else{
                count -= 1;
                current = finish_kakko+1;
            }
        }
        return current;

    }


    // ===================================================================================
    //  以下便利ツール

    private void log_answer(Object ans_text){
        log("[ans] -> " + ans_text);
    }

    private List<Object> get_content_list() {
        List<Object> contentList = new ArrayList<>();
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace boxSpace : spaceList) {
                Object content = boxSpace.getContent();
                contentList.add(content);
            }
        }

        if (contentList.isEmpty()) {
            throw new IllegalStateException("YourPrivateRoomにコンテンツが入っていません！！");
        }
        return contentList;
    }

    private List<String> get_strContent_list(){
        List<Object> content_list = get_content_list();
        List<String> new_content_list = new ArrayList<>();
        for (Object content : content_list) {
            if (content instanceof String){
                String contentStr = (String) content;
                new_content_list.add(contentStr);
            }
        }
        return new_content_list;
    }

    private List<java.io.File> get_fileContent_list(){
        List<Object> content_list = get_content_list();
        List<java.io.File> new_content_list = new ArrayList<>();
        for (Object content : content_list) {
            if (content instanceof java.io.File){
                java.io.File contentStr = (java.io.File) content;
                new_content_list.add(contentStr);
            }
        }
        return new_content_list;
    }

    private List<java.util.Map> get_mapContent_list(){
        List<Object> content_list = get_content_list();
        List<java.util.Map> new_content_list = new ArrayList<>();
        for (Object content : content_list) {
            if (content instanceof java.util.Map){
                java.util.Map contentStr = (java.util.Map) content;
                new_content_list.add(contentStr);
            }
        }
        return new_content_list;
    }
}