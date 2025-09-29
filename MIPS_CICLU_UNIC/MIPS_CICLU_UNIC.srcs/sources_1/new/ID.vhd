----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/02/2025 01:11:30 AM
-- Design Name: 
-- Module Name: ID - Behavioral
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

entity ID is
Port ( Instr: in std_logic_vector(25 downto 0);
       WD : in std_logic_vector(31 downto 0);
       RegWrite: in std_logic ;
       RegDst : in std_logic ;
       ExtOp: in std_logic;
       clk: in std_logic;
       RD1: out std_logic_vector(31 downto 0);
       RD2: out std_logic_vector(31 downto 0);
       ExtImm: out std_logic_vector(31 downto 0);
       func: out std_logic_vector(5 downto 0);
       sa: out std_logic_vector(4 downto 0 );
       enable: in std_logic); 
end ID;

architecture Behavioral of ID is

type reg_array is array(0 to 31) of std_logic_vector(31 downto 0);
signal RF : reg_array :=(others =>X"00000000");

signal writeAdress: std_logic_vector(4 downto 0);


begin


writeAdress <= Instr(15 downto 11) when RegDst = '1' else Instr(20 downto 16);  --rd/rt

process(clk)
begin
    if rising_edge(clk) then
        if RegWrite = '1' and enable = '1' then
            RF(conv_integer(writeAdress)) <= WD;
        end if;
    end if;
end process;

-- Citirea se face în functie de Instr direct
RD1 <= RF(conv_integer(Instr(25 downto 21)));  -- rs
RD2 <= RF(conv_integer(Instr(20 downto 16)));  -- rt
ExtImm(15 downto 0) <= Instr(15 downto 0);
ExtImm(31 downto 16) <= (others => Instr(15)) when ExtOp = '1' else (others => '0');            
sa <= Instr(10 downto 6);
func <= Instr(5 downto 0);

end Behavioral;
