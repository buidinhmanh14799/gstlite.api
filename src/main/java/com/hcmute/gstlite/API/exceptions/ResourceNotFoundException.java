/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hcmute.gstlite.API.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
