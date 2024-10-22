public class Main
{
    public static void main(String[] args)
    {

    }



}
    /// exercise7.4.1 ///
    /*

-- FUNCTION: public.employee_list_by_job(character varying)
-- DROP FUNCTION IF EXISTS public.employee_list_by_job(character varying);

CREATE OR REPLACE FUNCTION public.employee_list_by_job(
	p_job character varying)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
	RETURN QUERY SELECT * FROM employee WHERE job ILIKE p_job;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_job(character varying)
    OWNER TO postgres;

     */

    /// exercise7.4.2 ///

    /*

-- FUNCTION: public.employee_list_by_deptno(integer)
-- DROP FUNCTION IF EXISTS public.employee_list_by_deptno(integer);

CREATE OR REPLACE FUNCTION public.employee_list_by_deptno(
	p_deptno integer)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
	RETURN QUERY SELECT * FROM employee WHERE deptno = p_deptno;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_deptno(integer)
    OWNER TO postgres;

     */

    /// exercise 7.4.3 ///

    /*

-- FUNCTION: public.employee_list_by_name(character varying, character varying)
-- DROP FUNCTION IF EXISTS public.employee_list_by_name(character varying, character varying);

CREATE OR REPLACE FUNCTION public.employee_list_by_name(
	p_name character varying,
	p_wildcard character varying DEFAULT ''::character varying)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
DECLARE r_name VARCHAR;
BEGIN
	IF p_wildcard = 'a%' THEN
		r_name := p_name || '%';
	ELSIF p_wildcard = '%a' THEN
		r_name := '%' || p_name;
	ELSIF p_wildcard = '%a%' THEN
		r_name := '%' || p_name || '%';
	ELSE
		r_name := p_name;
	END IF;
	RETURN QUERY SELECT * FROM employee WHERE ename ILIKE r_name;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_name(character varying, character varying)
    OWNER TO postgres;

     */


