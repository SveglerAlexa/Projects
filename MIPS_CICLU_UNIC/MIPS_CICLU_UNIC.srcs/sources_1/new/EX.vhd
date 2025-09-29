----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/09/2025 12:26:49 AM
-- Design Name: 
-- Module Name: EX - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity EX is
Port (RD1: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      Ext_imm: in std_logic_vector(31 downto 0);
      sa: in std_logic_vector(4 downto 0);
      func: in std_logic_vector(5 downto 0);
      PC_4: in std_logic_vector(31 downto 0);
      ALUSrc: in std_logic ;
      ALUOp: in std_logic_vector(2 downto 0);
      clk: in std_logic ;
      ALURes: out std_logic_vector(31 downto 0);
      BranchAddress: out std_logic_vector(31 downto 0);
      Zero: out std_logic;
      GtZ: out std_logic );
end EX;



architecture Behavioral of EX is

signal ALUCtrl: std_logic_vector(2 downto 0);
signal intrALU: std_logic_vector(31 downto 0);
signal rezultat: std_logic_vector(31 downto 0);
signal ARD1: std_logic_vector(31 downto 0);
signal Z: std_logic;

begin

ARD1<=RD1;
intrALU<=RD2 when ALUSrc='0' else Ext_imm;


process(clk)
begin
    case ALUOp is
    when "010" => case func is
                    when "100000" => ALUCtrl<="000"; --(+) add
                    when "100010" => ALUCtrl<="110"; --(-) sub
                    when others => ALUCtrl<="XXX";
                  end case;
    when "001" => ALUCtrl<="001";-- andi (&)
    when "100" => ALUCtrl<="000";-- (+) addi/lw/sw
    when "011" => ALUCtrl<="110";-- (-) beq/bne/bgez
    when others => ALUCtrl <= (others=>'X');
    end case;
end process;


process(ARD1,intrALU,ALUCtrl,sa)
begin
    case ALUCtrl is
        when "000" => rezultat<=ARD1+intrALU;
        when "110" => rezultat<=ARD1-intrALU;
        when "001" => rezultat<=ARD1 and intrAlu;
        when others => rezultat<=(others=>'X');
    end case;
        
end process;

Z<='1' when rezultat=0 else '0';
Zero<= Z;
GtZ <= '1' when (RD1(31) = '0' or rezultat=0) else '0';  --primul bit din rezultat=0=>nr pozitiv 
ALURes<=rezultat;
BranchAddress<=PC_4+Ext_imm;

end Behavioral;
