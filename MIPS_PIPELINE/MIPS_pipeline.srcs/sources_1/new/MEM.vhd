----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/09/2025 01:03:20 AM
-- Design Name: 
-- Module Name: MEM - Behavioral
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

entity MEM is
Port (clk : in std_logic; 
      memWrite : in std_logic; 
      ALUResIN : in std_logic_vector(31 downto 0); 
      RD2_writeData : in std_logic_vector(31 downto 0); 
      memData : out std_logic_vector(31 downto 0);
      ALUResOUT: out std_logic_vector(31 downto 0);
      enable: in std_logic );
end MEM;

architecture Behavioral of MEM is

type ram_type is array (0 to 63) of std_logic_vector(31 downto 0); 
signal ram : ram_type := (  X"00000006", --nr max de iteratii
                            X"00000004",
                            X"00000002",
                            X"00000003",
                            X"00000004",
                            X"0000000A",
                            X"00000002",
                            others=>X"00000000"); 
                            
signal address: std_logic_vector(5 downto 0);
signal WriteData: std_logic_vector(31 downto 0);


begin

WriteData <= RD2_writeData;
Address <= AluResIn(5 downto 0);

process(clk) 
 begin 
    if rising_edge(clk) then 
        if enable='1' then
            if memWrite = '1' then 
                ram(conv_integer(Address)) <= WriteData; 
            end if; 
        end if; 
    end if;
 end process; 
 
memData <= ram(conv_integer(Address)); --CITIRE ASINCRONA
ALUResOUT<=ALUResIn;

end Behavioral;
