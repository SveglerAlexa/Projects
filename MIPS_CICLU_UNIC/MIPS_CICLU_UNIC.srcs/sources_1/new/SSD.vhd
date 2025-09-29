
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.all;
use IEEE.STD_LOGIC_ARITH.all;

entity SSD is
    Port ( clk  : in  STD_LOGIC;
           Rst  : in  STD_LOGIC;
           Digit : in  STD_LOGIC_VECTOR (31 downto 0);   -- datele pentru 8 cifre (cifra 1 din stanga: biti 31..28)
           an   : out STD_LOGIC_VECTOR (7 downto 0);    -- selectia anodului activ
           cat  : out STD_LOGIC_VECTOR (6 downto 0));   -- selectia catozilor (segmentelor) cifrei active
end SSD;

architecture Behavioral of SSD is


signal cnt: std_logic_vector(16 downto 0):=(others=>'0');
signal selectie: std_logic_vector(2 downto 0);
signal Hex : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');

begin

process(clk)
begin
   if rising_edge(CLK) then
   cnt<=cnt+1;
   end if;    
end process;

selectie <= cnt(16 downto 14);

-- Selectia anodului activ
    an <= "11111110" when selectie = "000" else
          "11111101" when selectie = "001" else
          "11111011" when selectie = "010" else
          "11110111" when selectie = "011" else
          "11101111" when selectie = "100" else
          "11011111" when selectie = "101" else
          "10111111" when selectie = "110" else
          "01111111" when selectie = "111" else
          "11111111";

-- Selectia cifrei active
    Hex <= Digit (3  downto  0) when selectie = "000" else
           Digit (7  downto  4) when selectie = "001" else
           Digit (11 downto  8) when selectie = "010" else
           Digit (15 downto 12) when selectie = "011" else
           Digit (19 downto 16) when selectie = "100" else
           Digit (23 downto 20) when selectie = "101" else
           Digit (27 downto 24) when selectie = "110" else
           Digit (31 downto 28) when selectie = "111" else
           X"0";

-- Activarea/dezactivarea segmentelor cifrei active
    cat <= "1111001" when Hex = "0001" else            -- 1
           "0100100" when Hex = "0010" else            -- 2
           "0110000" when Hex = "0011" else            -- 3
           "0011001" when Hex = "0100" else            -- 4
           "0010010" when Hex = "0101" else            -- 5
           "0000010" when Hex = "0110" else            -- 6
           "1111000" when Hex = "0111" else            -- 7
           "0000000" when Hex = "1000" else            -- 8
           "0010000" when Hex = "1001" else            -- 9
           "0001000" when Hex = "1010" else            -- A
           "0000011" when Hex = "1011" else            -- b
           "1000110" when Hex = "1100" else            -- C
           "0100001" when Hex = "1101" else            -- d
           "0000110" when Hex = "1110" else            -- E
           "0001110" when Hex = "1111" else            -- F
           "1000000";                                  -- 0

end Behavioral;
