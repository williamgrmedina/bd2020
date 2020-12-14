/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author Medina
 */
public interface FormatCurrency {
	public String getFormatted(BigDecimal value);
}
