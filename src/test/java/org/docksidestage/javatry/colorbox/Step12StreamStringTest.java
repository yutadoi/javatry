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
import java.lang.reflect.Array;
import java.security.KeyStore;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.docksidestage.bizfw.colorbox.ColorBox;
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

        int sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .mapToInt(var -> var.toString()
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

        log(strMax.get());
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
                            .map(var -> var.toString())
                            .filter(var -> var.startsWith(targetStr))
                            .collect(Collectors.toList());

                    return !collect.isEmpty();
                })
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
                    List<String> collect = colorBox.getSpaceList()
                            .stream()
                            .map(boxSpace -> boxSpace.getContent())
                            .filter(content -> content instanceof String)
                            .map(var -> var.toString())
                            .filter(var -> var.endsWith(targetStr))
                            .collect(Collectors.toList());

                    return !collect.isEmpty();
                })
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
        String targetStr = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<String> targetStrList = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList()
                        .stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(var -> var.toString())
                .filter(var -> var.endsWith(targetStr))
                .collect(Collectors.toList());

        for (String str : targetStrList) {
            log(str);
            log(str.indexOf(targetStr) + 1);
        }
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
                .filter(contentStr -> contentStr.indexOf(targetStr) != -1)
                .filter(contentStr -> {
                    int firstTarget = contentStr.indexOf(targetStr);
                    int lastTarget = contentStr.lastIndexOf(targetStr);
                    return firstTarget != lastTarget;
                })
                .forEach(var -> {
                    log("{} : {}", var.lastIndexOf(targetStr) + 1, var);
                });

        colorBoxList.forEach(new Consumer<ColorBox>() {
            @Override
            public void accept(ColorBox var) {
                log(var);
            }
        });

        colorBoxList.stream()
                .filter(new Predicate<ColorBox>() {
                    @Override
                    public boolean test(ColorBox colorBox) {
                        return colorBox instanceof YourPrivateRoom.DevilBox;
                    }
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
                .filter(contentStr -> contentStr.indexOf(targetStr) != -1)
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
                .map(devilBox -> {
                    try {
                        return devilBox.getText();
                    } catch (YourPrivateRoom.DevilBoxTextNotFoundException ignored) {
                        return "";
                    }
                })
                .mapToInt(devilStr -> devilStr.length())
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
                .peek(map -> log(((Map) map).toString()))
                .map(contentMap -> {
                    return ((Map) contentMap).keySet().stream()
                            .reduce(new StringBuilder("map:{"), (sb, key) -> {
                                return ((StringBuilder) sb).append("\"")
                                        .append(key)
                                        .append("\"=")
                                        .append(((Map) contentMap).get(key))
                                        .append("; ");
                            });
                })
                .forEach(var -> log(((StringBuilder)var).append("}"))); // TODO: 2019-05-30 ydoi ここが汚い。。

    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
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
        long count = stream.flatMap(colorBox -> colorBox.getSpaceList()
                .stream())
                .count();
        log(count);

        try {
            long count2 = stream.count();
            log(count2);
        } catch (IllegalStateException e) {
            log(e);
        }
    }
}