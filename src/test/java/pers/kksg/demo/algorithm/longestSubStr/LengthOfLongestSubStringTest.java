package pers.kksg.demo.algorithm.longestSubStr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthOfLongestSubStringTest {

    @Test
    void lengthOfLongestSubstring() {
        LengthOfLongestSubString length = new LengthOfLongestSubString();
        String str = "abcabcbb";
//        length.lengthOfLongestSubstring(str);
        int i = length.lengthOfLongestSubstring2(str);

    }
}