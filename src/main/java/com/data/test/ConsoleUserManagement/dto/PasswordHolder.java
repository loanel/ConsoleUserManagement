package com.data.test.ConsoleUserManagement.dto;

public class PasswordHolder implements CharSequence {
    private char[] passwordContainer;

    public PasswordHolder(char[] passwordContainer) {
        this.passwordContainer = passwordContainer;
    }

    @Override
    public int length() {
        return passwordContainer.length;
    }

    @Override
    public char charAt(int index) {
        return passwordContainer[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        char[] sequenceContainer = new char[end - start];
        int j = 0;
        for (int i = start; i < end; i++) {
            sequenceContainer[j] = passwordContainer[i];
        }
        return new PasswordHolder(sequenceContainer);
    }

    public char[] getCharArray() {
        return passwordContainer;
    }

    public String toString() {
        return new String(passwordContainer);
    }


}
