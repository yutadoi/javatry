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

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author yuta.doi
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor()) // consciously split as example
                .map(boxColor -> boxColor.getColorName())
                .map(colorName -> {
                    log(colorName); // for visual check
                    return String.valueOf(colorName.length());
                })
                .orElse("not found"); // basically no way because of not-empty list and not-null returns
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(var -> var.getContent())
                .filter(var -> var instanceof String)
                .peek(System.out::println)
                .map(var -> ((String) var))
                .max(Comparator.comparingInt(String::length))
                .ifPresent(s -> log(s));
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> contentStrList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(var -> var instanceof String)
                .map(var -> var.toString())
                .collect(Collectors.toList());

        if (colorBoxList.isEmpty()) {
            log("カラーボックスに文字列は存在しません");
            return;
        }

        Integer max = contentStrList.stream()
                .max(Comparator.comparingInt(String::length))
                .map(var -> {
                    log("{} {}", var.length(), var);
                    return var.length();
                })
                .get();

        Integer min = contentStrList.stream()
                .min(Comparator.comparingInt(String::length))
                .map(var -> {
                    log("{} {}", var.length(), var);
                    return var.length();
                })
                .get();

        log(max - min);

        // IntSummaryStatistics を使うことstream一回で実行ができる。

    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content != null)
                .map(content -> content.toString())
                .peek(var -> System.out.println(var.length() + " : " + var))
                .max(Comparator.comparingInt(String::length))
                .orElse(null);

        if (maxStr == null) {
            log("colorBoxListに値が存在しません");
            return;
        }
        log("\nmax str : {}", maxStr);

        String subMaxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content != null)
                .map(content -> content.toString())
                .filter(str -> !str.equals(maxStr))
                .max(Comparator.comparingInt(String::length))
                .orElse(null);

        if (subMaxStr == null) {
            log("colorBoxListに値が存在しません");
            return;
        }
        log("sub max str : {}", subMaxStr);

        // sortedを使うことができる。
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        // colorBoxList = new ArrayList<>(); // 空だった場合の出力は分けるべき？

        // 何も入っていないことを判定するために、SumResultクラスを作るとこもある
        int sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .mapToInt(content -> content.toString()
                        .length())
                .sum();

        log(sum);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> strMax = colorBoxList.stream()
                .map(colorBox -> colorBox.getColor()
                        .getColorName())
                .max(Comparator.comparingInt(String::length));

        strMax.ifPresentOrElse(
                var -> log(var),
                () -> log("not found")
        );
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        String targetStr = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<String> ansList = colorBoxList.stream()
                .filter(colorBox -> {
                    List<String> collect = colorBox.getSpaceList()
                            .stream()
                            .map(boxSpace -> boxSpace.getContent())
                            .filter(content -> content instanceof String)
                            .map(content -> content.toString())
                            .filter(content -> content.startsWith(targetStr))
                            .collect(Collectors.toList());

                    return !collect.isEmpty();
                })
                .map(colorBox -> colorBox.getColor()
                        .getColorName())
                .collect(Collectors.toList());

        log(ansList);

    }

    public void test_startsWith_findFirstWordSub() {
        String targetStr = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<String> ansList = colorBoxList.stream()
                .filter(colorBox -> {
                    return colorBox.getSpaceList()
                            .stream()
                            .map(boxSpace -> boxSpace.getContent())
                            .filter(content -> content instanceof String)
                            .map(content -> content.toString())
                            .anyMatch(content -> content.startsWith(targetStr));
                })
                .peek(var -> log(var.getSpaceList()))
                .map(colorBox -> colorBox.getColor()
                        .getColorName())
                .collect(Collectors.toList());

        log(ansList);
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        String targetStr = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<String> ansList = colorBoxList.stream()
                .filter(colorBox -> {
                    return colorBox.getSpaceList()
                            .stream()
                            .map(boxSpace -> boxSpace.getContent())
                            .filter(content -> content instanceof String)
                            .map(var -> var.toString())
                            .anyMatch(var -> var.endsWith(targetStr));
                })
                .peek(var -> log(var.getSpaceList()))
                .map(colorBox -> colorBox.getColor()
                        .getColorName())
                .collect(Collectors.toList());
        log(ansList);
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with "front" of string ending with "front" in color-boxes? <br>
     * (あなたのカラーボックスに入ってる "front" で終わる文字列で、"front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        String targetStr = "front"; // 後のコードの可読性をあげるために、ここの変数は二つにする方が良いパターンもある。
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        // 値を返す場合は、foundIdx、existIdx
        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(strContent -> strContent.endsWith(targetStr))
                .peek(strContent -> log(strContent))
                .forEach(strContent -> log(strContent.indexOf(targetStr) + 1));
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (あなたのカラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        String targetStr = "ど";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .filter(contentStr -> contentStr.contains(targetStr))// ここは必要ない
//                .filter(contentStr -> contentStr.indexOf(targetStr) != -1)// ここは必要ない
                .filter(contentStr -> {
                    int firstTarget = contentStr.indexOf(targetStr);
                    int lastTarget = contentStr.lastIndexOf(targetStr);
                    return firstTarget != lastTarget;
                })
                .forEach(var -> {
                    log("{} : {}", var.lastIndexOf(targetStr) + 1, var);
                });
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        String targetStr = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .filter(contentStr -> contentStr.endsWith(targetStr))
                .forEach(var -> {
                    log(var);
                    log(var.substring(0, 1));
                });
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        String targetStr = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .filter(contentStr -> contentStr.startsWith(targetStr))
                .forEach(var -> {
                    log(var);
                    log(var.substring(var.length() - 1));
                });
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        String targetStr = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> ((String) content))
                .filter(contentStr -> contentStr.contains(targetStr))
//                .filter(contentStr -> contentStr.indexOf(targetStr) != -1)
                .map(contentStr -> contentStr.replace("o", ""))
                .forEach(var -> {
                    log(var);
                    log(var.length());
                });
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        String linuxFileSeparator = "/";
        String winFileSeparator = "\\";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof java.io.File)
                .map(content -> ((File) content).getPath())
                .map(contentPath -> contentPath.replace(linuxFileSeparator, winFileSeparator))
                .forEach(var -> log(var));

    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        if (colorBoxList.isEmpty()) {
            log("colorBoxListに値が存在しません");
            return;
        }

        int sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof YourPrivateRoom.DevilBox)
                .map(devilBox -> ((YourPrivateRoom.DevilBox) devilBox))
                .peek(devilBox -> devilBox.wakeUp())
                .peek(devilBox -> devilBox.allowMe())
                .peek(devilBox -> devilBox.open())
                .mapToInt(devilBox -> {
                    try {
                        return devilBox.getText().length();
                    } catch (YourPrivateRoom.DevilBoxTextNotFoundException ignored) {
                        return 0;
                    }
                })
                .sum();

        log(sum);

    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Map)
                .peek(contentMap -> log(contentMap.toString()))
                .map(contentMap -> {
                    Map<Object, Object> contentMapE = ((Map<Object, Object>) contentMap);
                    return toStringFromSimpleMap(contentMapE);
                })
                .forEach(var -> log(((StringBuilder)var).append("}"))); // TODO: 2019-05-30 ydoi ここが汚い。。

    }


    private StringBuilder toStringFromSimpleMap(Map<Object, Object> contentMap) {
        return contentMap.entrySet()
                .stream()
                .map(entry -> (new StringBuilder()
                        .append("\"")
                        .append(entry.getKey())
                        .append("\"=")
                        .append(entry.getValue())
                        .append("; ")))
                .reduce(new StringBuilder("map:{"), (result, entry) -> result.append(entry));
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Map)
                .map(contentMap -> convertStrFromMap(((Map) contentMap)))
                .forEach(contentStr -> log(contentStr));
    }

    private String convertStrFromMap(java.util.Map map){
        StringBuilder show_map = new StringBuilder("map:{ ");

        boolean isFinish = false;
        for (Object key : map.keySet()) {
            if (isFinish) show_map.append(" ; "); // 最後の、セミコロンは入れない

            Object value = map.get(key);
            String valueStr;
            if (value instanceof Map) {
                Map valueMap = (Map) value;
                valueStr = convertStrFromMap(valueMap);
            }else{
                valueStr = value.toString();
            }
            show_map.append(key.toString()).append(" = ").append(valueStr);
            isFinish = true;
        }
        return show_map.append(" }").toString();
    }








    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}

    /************ 体験会場 ************
     /**
     * stream の終端を体験する
     */
    public void test_stream_terminator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Stream<ColorBox> stream = colorBoxList.stream();
        long count = stream.mapToLong(colorBox -> colorBox.getSpaceList()
                .size())
                .sum();
        log(count);

        try {
            long count2 = stream.count();
            log(count2);
        } catch (IllegalStateException e) {
            log(e);
        }
    }


    public void test_optional() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        Optional<ColorBox> optBox = colorBoxList.stream().findFirst();
        Optional<BoxSpace> boxSpace = optBox.flatMap(colorBox -> colorBox.getSpaceList()
                .stream()
                .findFirst());

        String logData = boxSpace.map(var -> var.getContent()
                .toString())
                .orElseGet(() -> {
                    return "not found";
                });

        log(logData);
    }


    class Member{
        private String name;

        public String getName() {
            return name;
        }

        public Member(String name) {
            this.name = name;
        }

    }


    public void test_member() {

        Optional<Member> optMember1 = Optional.of(new Member(null));
        Optional<Member> optMember2 = Optional.empty();

        List<Optional<Member>> memberList = Arrays.asList(optMember1, optMember2);

        for (Optional<Member> member : memberList) {
            log(member.map(Member::getName)
                    .orElse("not found"));
        }
    }

    public void test_memberNotStream() {

        List<Member> memberList = Arrays.asList(
                new Member(null),
                null);

        for (Member member : memberList) {
            log(getMemberName(member));
        }
    }

    private String getMemberName(Member member) {
        if (member != null){
            if (member.getName() != null) {
                return member.getName();
            }
        }
        return "not found";
    }
    public void test_reduce() {
        final List<String> list = new ArrayList<>();

        list.add("one");
        list.add("two");
        list.add("three");

        final Optional<String> result = list.stream().reduce(
                (accum, value) -> {
                    return accum + "-"  + value;
                });

        result.ifPresent(var -> log(var));

    }

}