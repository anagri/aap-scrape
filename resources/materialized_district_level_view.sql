SELECT
  'country',
  'state',
  'district',
  'num_donors',
  'total_amount',
  'avg_amount'
UNION
(SELECT
   country,
   state,
   district,
   count(*)              AS num_donors,
   sum(amount)           AS total_amount,
   round(avg(amount), 2) AS avg_amount
 INTO OUTFILE 'materialized_district_level_view.csv' FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
 FROM donations
#  WHERE country = 'INDIA'
 GROUP BY country, state, district
 ORDER BY country, state, district);