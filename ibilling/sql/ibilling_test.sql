--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.user_role_map DROP CONSTRAINT user_role_map_fk_2;
ALTER TABLE ONLY public.user_role_map DROP CONSTRAINT user_role_map_fk_1;
ALTER TABLE ONLY public.role DROP CONSTRAINT role_entity_id_fk;
ALTER TABLE ONLY public.entity_report_map DROP CONSTRAINT report_map_report_id_fk;
ALTER TABLE ONLY public.entity_report_map DROP CONSTRAINT report_map_entity_id_fk;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_fk_5;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_fk_4;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_fk_3;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_fk_2;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_fk_1;
ALTER TABLE ONLY public.promotion_user_map DROP CONSTRAINT promotion_user_map_fk_2;
ALTER TABLE ONLY public.promotion_user_map DROP CONSTRAINT promotion_user_map_fk_1;
ALTER TABLE ONLY public.promotion DROP CONSTRAINT promotion_fk_1;
ALTER TABLE ONLY public.process_run_user DROP CONSTRAINT process_run_user_fk_2;
ALTER TABLE ONLY public.process_run_user DROP CONSTRAINT process_run_user_fk_1;
ALTER TABLE ONLY public.process_run_total_pm DROP CONSTRAINT process_run_total_pm_fk_1;
ALTER TABLE ONLY public.process_run_total DROP CONSTRAINT process_run_total_fk_2;
ALTER TABLE ONLY public.process_run_total DROP CONSTRAINT process_run_total_fk_1;
ALTER TABLE ONLY public.process_run DROP CONSTRAINT process_run_fk_2;
ALTER TABLE ONLY public.process_run DROP CONSTRAINT process_run_fk_1;
ALTER TABLE ONLY public.preference DROP CONSTRAINT preference_fk_2;
ALTER TABLE ONLY public.preference DROP CONSTRAINT preference_fk_1;
ALTER TABLE ONLY public.pluggable_task_type DROP CONSTRAINT pluggable_task_type_fk_1;
ALTER TABLE ONLY public.pluggable_task_parameter DROP CONSTRAINT pluggable_task_parameter_fk_1;
ALTER TABLE ONLY public.pluggable_task DROP CONSTRAINT pluggable_task_fk_2;
ALTER TABLE ONLY public.pluggable_task DROP CONSTRAINT pluggable_task_fk_1;
ALTER TABLE ONLY public.permission_user DROP CONSTRAINT permission_user_fk_2;
ALTER TABLE ONLY public.permission_user DROP CONSTRAINT permission_user_fk_1;
ALTER TABLE ONLY public.permission_role_map DROP CONSTRAINT permission_role_map_fk_2;
ALTER TABLE ONLY public.permission_role_map DROP CONSTRAINT permission_role_map_fk_1;
ALTER TABLE ONLY public.permission DROP CONSTRAINT permission_fk_1;
ALTER TABLE ONLY public.payment_invoice DROP CONSTRAINT payment_invoice_fk_2;
ALTER TABLE ONLY public.payment_invoice DROP CONSTRAINT payment_invoice_fk_1;
ALTER TABLE ONLY public.payment_info_cheque DROP CONSTRAINT payment_info_cheque_fk_1;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_6;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_5;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_4;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_3;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_2;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_fk_1;
ALTER TABLE ONLY public.payment_authorization DROP CONSTRAINT payment_authorization_fk_1;
ALTER TABLE ONLY public.partner_payout DROP CONSTRAINT partner_payout_fk_1;
ALTER TABLE ONLY public.partner DROP CONSTRAINT partner_fk_4;
ALTER TABLE ONLY public.partner DROP CONSTRAINT partner_fk_3;
ALTER TABLE ONLY public.partner DROP CONSTRAINT partner_fk_2;
ALTER TABLE ONLY public.partner DROP CONSTRAINT partner_fk_1;
ALTER TABLE ONLY public.order_process DROP CONSTRAINT order_process_fk_1;
ALTER TABLE ONLY public.order_period DROP CONSTRAINT order_period_fk_2;
ALTER TABLE ONLY public.order_period DROP CONSTRAINT order_period_fk_1;
ALTER TABLE ONLY public.order_line DROP CONSTRAINT order_line_fk_3;
ALTER TABLE ONLY public.order_line DROP CONSTRAINT order_line_fk_2;
ALTER TABLE ONLY public.order_line DROP CONSTRAINT order_line_fk_1;
ALTER TABLE ONLY public.notification_message_section DROP CONSTRAINT notification_message_section_fk_1;
ALTER TABLE ONLY public.notification_message_line DROP CONSTRAINT notification_message_line_fk_1;
ALTER TABLE ONLY public.notification_message DROP CONSTRAINT notification_message_fk_3;
ALTER TABLE ONLY public.notification_message DROP CONSTRAINT notification_message_fk_2;
ALTER TABLE ONLY public.notification_message DROP CONSTRAINT notification_message_fk_1;
ALTER TABLE ONLY public.notification_message_arch_line DROP CONSTRAINT notif_mess_arch_line_fk_1;
ALTER TABLE ONLY public.mediation_record_line DROP CONSTRAINT mediation_record_line_fk_2;
ALTER TABLE ONLY public.mediation_record_line DROP CONSTRAINT mediation_record_line_fk_1;
ALTER TABLE ONLY public.mediation_record DROP CONSTRAINT mediation_record_fk_2;
ALTER TABLE ONLY public.mediation_record DROP CONSTRAINT mediation_record_fk_1;
ALTER TABLE ONLY public.mediation_process DROP CONSTRAINT mediation_process_fk_1;
ALTER TABLE ONLY public.mediation_order_map DROP CONSTRAINT mediation_order_map_fk_2;
ALTER TABLE ONLY public.mediation_order_map DROP CONSTRAINT mediation_order_map_fk_1;
ALTER TABLE ONLY public.mediation_cfg DROP CONSTRAINT mediation_cfg_fk_1;
ALTER TABLE ONLY public.item_type_map DROP CONSTRAINT item_type_map_fk_2;
ALTER TABLE ONLY public.item_type_map DROP CONSTRAINT item_type_map_fk_1;
ALTER TABLE ONLY public.item_type DROP CONSTRAINT item_type_fk_1;
ALTER TABLE ONLY public.item_type_exclude_map DROP CONSTRAINT item_type_exclude_type_id_fk;
ALTER TABLE ONLY public.item_type_exclude_map DROP CONSTRAINT item_type_exclude_item_id_fk;
ALTER TABLE ONLY public.item_price DROP CONSTRAINT item_price_fk_2;
ALTER TABLE ONLY public.item_price DROP CONSTRAINT item_price_fk_1;
ALTER TABLE ONLY public.item DROP CONSTRAINT item_fk_1;
ALTER TABLE ONLY public.invoice_line DROP CONSTRAINT invoice_line_fk_3;
ALTER TABLE ONLY public.invoice_line DROP CONSTRAINT invoice_line_fk_2;
ALTER TABLE ONLY public.invoice_line DROP CONSTRAINT invoice_line_fk_1;
ALTER TABLE ONLY public.invoice DROP CONSTRAINT invoice_fk_4;
ALTER TABLE ONLY public.invoice DROP CONSTRAINT invoice_fk_3;
ALTER TABLE ONLY public.invoice DROP CONSTRAINT invoice_fk_2;
ALTER TABLE ONLY public.invoice DROP CONSTRAINT invoice_fk_1;
ALTER TABLE ONLY public.international_description DROP CONSTRAINT international_description_fk_1;
ALTER TABLE ONLY public.generic_status DROP CONSTRAINT generic_status_fk_1;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_6;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_5;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_4;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_3;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_2;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_fk_1;
ALTER TABLE ONLY public.entity_payment_method_map DROP CONSTRAINT entity_payment_method_map_fk_2;
ALTER TABLE ONLY public.entity_payment_method_map DROP CONSTRAINT entity_payment_method_map_fk_1;
ALTER TABLE ONLY public.entity DROP CONSTRAINT entity_fk_2;
ALTER TABLE ONLY public.entity DROP CONSTRAINT entity_fk_1;
ALTER TABLE ONLY public.entity_delivery_method_map DROP CONSTRAINT entity_delivery_method_map_fk_2;
ALTER TABLE ONLY public.entity_delivery_method_map DROP CONSTRAINT entity_delivery_method_map_fk_1;
ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_fk_3;
ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_fk_2;
ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_fk_1;
ALTER TABLE ONLY public.currency_exchange DROP CONSTRAINT currency_exchange_fk_1;
ALTER TABLE ONLY public.currency_entity_map DROP CONSTRAINT currency_entity_map_fk_2;
ALTER TABLE ONLY public.currency_entity_map DROP CONSTRAINT currency_entity_map_fk_1;
ALTER TABLE ONLY public.contact_type DROP CONSTRAINT contact_type_fk_1;
ALTER TABLE ONLY public.contact_map DROP CONSTRAINT contact_map_fk_3;
ALTER TABLE ONLY public.contact_map DROP CONSTRAINT contact_map_fk_2;
ALTER TABLE ONLY public.contact_map DROP CONSTRAINT contact_map_fk_1;
ALTER TABLE ONLY public.contact_field_type DROP CONSTRAINT contact_field_type_fk_1;
ALTER TABLE ONLY public.contact_field DROP CONSTRAINT contact_field_fk_2;
ALTER TABLE ONLY public.contact_field DROP CONSTRAINT contact_field_fk_1;
ALTER TABLE ONLY public.notification_message_type DROP CONSTRAINT category_id_fk_1;
ALTER TABLE ONLY public.blacklist DROP CONSTRAINT blacklist_fk_2;
ALTER TABLE ONLY public.blacklist DROP CONSTRAINT blacklist_fk_1;
ALTER TABLE ONLY public.billing_process DROP CONSTRAINT billing_process_fk_3;
ALTER TABLE ONLY public.billing_process DROP CONSTRAINT billing_process_fk_2;
ALTER TABLE ONLY public.billing_process DROP CONSTRAINT billing_process_fk_1;
ALTER TABLE ONLY public.billing_process_configuration DROP CONSTRAINT billing_process_configuration_fk_2;
ALTER TABLE ONLY public.billing_process_configuration DROP CONSTRAINT billing_process_configuration_fk_1;
ALTER TABLE ONLY public.base_user DROP CONSTRAINT base_user_fk_5;
ALTER TABLE ONLY public.base_user DROP CONSTRAINT base_user_fk_4;
ALTER TABLE ONLY public.base_user DROP CONSTRAINT base_user_fk_3;
ALTER TABLE ONLY public.ageing_entity_step DROP CONSTRAINT ageing_entity_step_fk_2;
ALTER TABLE ONLY public.ach DROP CONSTRAINT ach_fk_1;
DROP INDEX public.user_role_map_i_role;
DROP INDEX public.user_role_map_i_2;
DROP INDEX public.user_credit_card_map_i_2;
DROP INDEX public.transaction_id;
DROP INDEX public.purchase_order_i_user;
DROP INDEX public.purchase_order_i_notif;
DROP INDEX public.promotion_user_map_i_2;
DROP INDEX public.permission_user_map_i_2;
DROP INDEX public.permission_role_map_i_2;
DROP INDEX public.payment_i_3;
DROP INDEX public.payment_i_2;
DROP INDEX public.partner_range_p;
DROP INDEX public.partner_payout_i_2;
DROP INDEX public.partner_i_3;
DROP INDEX public.mediation_record_i;
DROP INDEX public.ix_uq_payment_inv_map_pa_in;
DROP INDEX public.ix_uq_order_process_or_in;
DROP INDEX public.ix_uq_order_process_or_bp;
DROP INDEX public.ix_purchase_order_date;
DROP INDEX public.ix_promotion_code;
DROP INDEX public.ix_pa_payment;
DROP INDEX public.ix_order_process_in;
DROP INDEX public.ix_order_line_order_id;
DROP INDEX public.ix_order_line_item_id;
DROP INDEX public.ix_mrl_order_line;
DROP INDEX public.ix_item_ent;
DROP INDEX public.ix_invoice_user_id;
DROP INDEX public.ix_invoice_ts;
DROP INDEX public.ix_invoice_process;
DROP INDEX public.ix_invoice_number;
DROP INDEX public.ix_invoice_line_invoice_id;
DROP INDEX public.ix_invoice_due_date;
DROP INDEX public.ix_invoice_date;
DROP INDEX public.ix_el_main;
DROP INDEX public.ix_contact_phone;
DROP INDEX public.ix_contact_orgname;
DROP INDEX public.ix_contact_lname;
DROP INDEX public.ix_contact_fname_lname;
DROP INDEX public.ix_contact_fname;
DROP INDEX public.ix_contact_field_content;
DROP INDEX public.ix_contact_field_cid;
DROP INDEX public.ix_contact_address;
DROP INDEX public.ix_cf_type_entity;
DROP INDEX public.ix_cc_number_encrypted;
DROP INDEX public.ix_cc_number;
DROP INDEX public.ix_blacklist_user_type;
DROP INDEX public.ix_blacklist_entity_type;
DROP INDEX public.ix_base_user_un;
DROP INDEX public.international_description_i_2;
DROP INDEX public.int_description_i_lan;
DROP INDEX public.customer_i_2;
DROP INDEX public.currency_entity_map_i_2;
DROP INDEX public.create_datetime;
DROP INDEX public.contact_map_i_3;
DROP INDEX public.contact_map_i_1;
DROP INDEX public.contact_i_del;
DROP INDEX public.bp_run_total_run_ix;
DROP INDEX public.bp_run_process_ix;
DROP INDEX public.bp_pm_index_total;
DROP INDEX public.ach_i_2;
ALTER TABLE ONLY public.shortcut DROP CONSTRAINT shortcut_pkey;
ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
ALTER TABLE ONLY public.report_type DROP CONSTRAINT report_type_pkey;
ALTER TABLE ONLY public.report DROP CONSTRAINT report_pkey;
ALTER TABLE ONLY public.report_parameter DROP CONSTRAINT report_parameter_pkey;
ALTER TABLE ONLY public.recent_item DROP CONSTRAINT recent_item_pkey;
ALTER TABLE ONLY public.purchase_order DROP CONSTRAINT purchase_order_pkey;
ALTER TABLE ONLY public.promotion DROP CONSTRAINT promotion_pkey;
ALTER TABLE ONLY public.process_run_user DROP CONSTRAINT process_run_user_pkey;
ALTER TABLE ONLY public.process_run_total_pm DROP CONSTRAINT process_run_total_pm_pkey;
ALTER TABLE ONLY public.process_run_total DROP CONSTRAINT process_run_total_pkey;
ALTER TABLE ONLY public.process_run DROP CONSTRAINT process_run_pkey;
ALTER TABLE ONLY public.preference_type DROP CONSTRAINT preference_type_pkey;
ALTER TABLE ONLY public.preference DROP CONSTRAINT preference_pkey;
ALTER TABLE ONLY public.pluggable_task_type DROP CONSTRAINT pluggable_task_type_pkey;
ALTER TABLE ONLY public.pluggable_task_type_category DROP CONSTRAINT pluggable_task_type_category_pkey;
ALTER TABLE ONLY public.pluggable_task DROP CONSTRAINT pluggable_task_pkey;
ALTER TABLE ONLY public.pluggable_task_parameter DROP CONSTRAINT pluggable_task_parameter_pkey;
ALTER TABLE ONLY public.permission_user DROP CONSTRAINT permission_user_pkey;
ALTER TABLE ONLY public.permission_type DROP CONSTRAINT permission_type_pkey;
ALTER TABLE ONLY public.permission DROP CONSTRAINT permission_pkey;
ALTER TABLE ONLY public.period_unit DROP CONSTRAINT period_unit_pkey;
ALTER TABLE ONLY public.payment_result DROP CONSTRAINT payment_result_pkey;
ALTER TABLE ONLY public.payment DROP CONSTRAINT payment_pkey;
ALTER TABLE ONLY public.payment_method DROP CONSTRAINT payment_method_pkey;
ALTER TABLE ONLY public.payment_invoice DROP CONSTRAINT payment_invoice_pkey;
ALTER TABLE ONLY public.payment_info_cheque DROP CONSTRAINT payment_info_cheque_pkey;
ALTER TABLE ONLY public.payment_authorization DROP CONSTRAINT payment_authorization_pkey;
ALTER TABLE ONLY public.partner_range DROP CONSTRAINT partner_range_pkey;
ALTER TABLE ONLY public.partner DROP CONSTRAINT partner_pkey;
ALTER TABLE ONLY public.partner_payout DROP CONSTRAINT partner_payout_pkey;
ALTER TABLE ONLY public.paper_invoice_batch DROP CONSTRAINT paper_invoice_batch_pkey;
ALTER TABLE ONLY public.order_process DROP CONSTRAINT order_process_pkey;
ALTER TABLE ONLY public.order_period DROP CONSTRAINT order_period_pkey;
ALTER TABLE ONLY public.order_line_type DROP CONSTRAINT order_line_type_pkey;
ALTER TABLE ONLY public.order_line DROP CONSTRAINT order_line_pkey;
ALTER TABLE ONLY public.order_billing_type DROP CONSTRAINT order_billing_type_pkey;
ALTER TABLE ONLY public.notification_message_type DROP CONSTRAINT notification_message_type_pkey;
ALTER TABLE ONLY public.notification_message_section DROP CONSTRAINT notification_message_section_pkey;
ALTER TABLE ONLY public.notification_message DROP CONSTRAINT notification_message_pkey;
ALTER TABLE ONLY public.notification_message_line DROP CONSTRAINT notification_message_line_pkey;
ALTER TABLE ONLY public.notification_message_arch DROP CONSTRAINT notification_message_arch_pkey;
ALTER TABLE ONLY public.notification_message_arch_line DROP CONSTRAINT notification_message_arch_line_pkey;
ALTER TABLE ONLY public.notification_category DROP CONSTRAINT notification_category_pk;
ALTER TABLE ONLY public.mediation_record DROP CONSTRAINT mediation_record_pkey;
ALTER TABLE ONLY public.mediation_record_line DROP CONSTRAINT mediation_record_line_pkey;
ALTER TABLE ONLY public.mediation_process DROP CONSTRAINT mediation_process_pkey;
ALTER TABLE ONLY public.mediation_errors DROP CONSTRAINT mediation_errors_pkey;
ALTER TABLE ONLY public.mediation_cfg DROP CONSTRAINT mediation_cfg_pkey;
ALTER TABLE ONLY public.language DROP CONSTRAINT language_pkey;
ALTER TABLE ONLY public.ibilling_table DROP CONSTRAINT ibilling_table_pkey;
ALTER TABLE ONLY public.item_type DROP CONSTRAINT item_type_pkey;
ALTER TABLE ONLY public.item_type_exclude_map DROP CONSTRAINT item_type_exclude_map_pkey;
ALTER TABLE ONLY public.item DROP CONSTRAINT item_pkey;
ALTER TABLE ONLY public.invoice DROP CONSTRAINT invoice_pkey;
ALTER TABLE ONLY public.invoice_line_type DROP CONSTRAINT invoice_line_type_pkey;
ALTER TABLE ONLY public.invoice_line DROP CONSTRAINT invoice_line_pkey;
ALTER TABLE ONLY public.invoice_delivery_method DROP CONSTRAINT invoice_delivery_method_pkey;
ALTER TABLE ONLY public.international_description DROP CONSTRAINT international_description_pkey;
ALTER TABLE ONLY public.generic_status_type DROP CONSTRAINT generic_status_type_pkey;
ALTER TABLE ONLY public.generic_status DROP CONSTRAINT generic_status_pkey;
ALTER TABLE ONLY public.filter_set DROP CONSTRAINT filter_set_pkey;
ALTER TABLE ONLY public.filter DROP CONSTRAINT filter_pkey;
ALTER TABLE ONLY public.event_log DROP CONSTRAINT event_log_pkey;
ALTER TABLE ONLY public.event_log_module DROP CONSTRAINT event_log_module_pkey;
ALTER TABLE ONLY public.event_log_message DROP CONSTRAINT event_log_message_pkey;
ALTER TABLE ONLY public.entity DROP CONSTRAINT entity_pkey;
ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
ALTER TABLE ONLY public.currency DROP CONSTRAINT currency_pkey;
ALTER TABLE ONLY public.currency_exchange DROP CONSTRAINT currency_exchange_pkey;
ALTER TABLE ONLY public.credit_card DROP CONSTRAINT credit_card_pkey;
ALTER TABLE ONLY public.country DROP CONSTRAINT country_pkey;
ALTER TABLE ONLY public.contact_type DROP CONSTRAINT contact_type_pkey;
ALTER TABLE ONLY public.contact DROP CONSTRAINT contact_pkey;
ALTER TABLE ONLY public.contact_map DROP CONSTRAINT contact_map_pkey;
ALTER TABLE ONLY public.contact_field_type DROP CONSTRAINT contact_field_type_pkey;
ALTER TABLE ONLY public.contact_field DROP CONSTRAINT contact_field_pkey;
ALTER TABLE ONLY public.cdrentries DROP CONSTRAINT cdrentries_pkey;
ALTER TABLE ONLY public.breadcrumb DROP CONSTRAINT breadcrumb_pkey;
ALTER TABLE ONLY public.blacklist DROP CONSTRAINT blacklist_pkey;
ALTER TABLE ONLY public.billing_process DROP CONSTRAINT billing_process_pkey;
ALTER TABLE ONLY public.billing_process_configuration DROP CONSTRAINT billing_process_configuration_pkey;
ALTER TABLE ONLY public.base_user DROP CONSTRAINT base_user_pkey;
ALTER TABLE ONLY public.ageing_entity_step DROP CONSTRAINT ageing_entity_step_pkey;
ALTER TABLE ONLY public.ach DROP CONSTRAINT ach_pkey;
DROP TABLE public.user_role_map;
DROP TABLE public.user_credit_card_map;
DROP TABLE public.shortcut;
DROP TABLE public.role;
DROP TABLE public.report_type;
DROP TABLE public.report_parameter;
DROP TABLE public.report;
DROP TABLE public.recent_item;
DROP TABLE public.purchase_order;
DROP TABLE public.promotion_user_map;
DROP TABLE public.promotion;
DROP TABLE public.process_run_user;
DROP TABLE public.process_run_total_pm;
DROP TABLE public.process_run_total;
DROP TABLE public.process_run;
DROP TABLE public.preference_type;
DROP TABLE public.preference;
DROP TABLE public.pluggable_task_type_category;
DROP TABLE public.pluggable_task_type;
DROP TABLE public.pluggable_task_parameter;
DROP TABLE public.pluggable_task;
DROP TABLE public.permission_user;
DROP TABLE public.permission_type;
DROP TABLE public.permission_role_map;
DROP TABLE public.permission;
DROP TABLE public.period_unit;
DROP TABLE public.payment_result;
DROP TABLE public.payment_method;
DROP TABLE public.payment_invoice;
DROP TABLE public.payment_info_cheque;
DROP TABLE public.payment_authorization;
DROP TABLE public.payment;
DROP TABLE public.partner_range;
DROP TABLE public.partner_payout;
DROP TABLE public.partner;
DROP TABLE public.paper_invoice_batch;
DROP TABLE public.order_process;
DROP TABLE public.order_period;
DROP TABLE public.order_line_type;
DROP TABLE public.order_line;
DROP TABLE public.order_billing_type;
DROP TABLE public.notification_message_type;
DROP TABLE public.notification_message_section;
DROP TABLE public.notification_message_line;
DROP TABLE public.notification_message_arch_line;
DROP TABLE public.notification_message_arch;
DROP TABLE public.notification_message;
DROP TABLE public.notification_category;
DROP TABLE public.mediation_record_line;
DROP TABLE public.mediation_record;
DROP TABLE public.mediation_process;
DROP TABLE public.mediation_order_map;
DROP TABLE public.mediation_errors;
DROP TABLE public.mediation_cfg;
DROP TABLE public.language;
DROP TABLE public.ibilling_constant;
DROP TABLE public.ibilling_table;
DROP TABLE public.ibilling_seqs;
DROP TABLE public.item_type_map;
DROP TABLE public.item_type_exclude_map;
DROP TABLE public.item_type;
DROP TABLE public.item_price;
DROP TABLE public.item;
DROP TABLE public.invoice_line_type;
DROP TABLE public.invoice_line;
DROP TABLE public.invoice_delivery_method;
DROP TABLE public.invoice;
DROP TABLE public.international_description;
DROP TABLE public.generic_status_type;
DROP TABLE public.generic_status;
DROP TABLE public.filter_set_filter;
DROP TABLE public.filter_set;
DROP TABLE public.filter;
DROP TABLE public.event_log_module;
DROP TABLE public.event_log_message;
DROP TABLE public.event_log;
DROP TABLE public.entity_report_map;
DROP TABLE public.entity_payment_method_map;
DROP TABLE public.entity_delivery_method_map;
DROP TABLE public.entity;
DROP TABLE public.customer;
DROP TABLE public.currency_exchange;
DROP TABLE public.currency_entity_map;
DROP TABLE public.currency;
DROP TABLE public.credit_card;
DROP TABLE public.country;
DROP TABLE public.contact_type;
DROP TABLE public.contact_map;
DROP TABLE public.contact_field_type;
DROP TABLE public.contact_field;
DROP TABLE public.contact;
DROP TABLE public.cdrentries;
DROP TABLE public.breadcrumb;
DROP TABLE public.blacklist;
DROP TABLE public.billing_process_configuration;
DROP TABLE public.billing_process;
DROP TABLE public.base_user;
DROP TABLE public.ageing_entity_step;
DROP TABLE public.ach;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 259 (class 3079 OID 34684)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2734 (class 0 OID 0)
-- Dependencies: 259
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 34689)
-- Dependencies: 2252 6
-- Name: ach; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE ach (
    id integer NOT NULL,
    user_id integer,
    aba_routing character varying(40) NOT NULL,
    bank_account character varying(60) NOT NULL,
    account_type integer NOT NULL,
    bank_name character varying(50) NOT NULL,
    account_name character varying(100) NOT NULL,
    optlock integer NOT NULL,
    gateway_key character varying(100) DEFAULT NULL::character varying
);


ALTER TABLE public.ach OWNER TO ibilling;

--
-- TOC entry 162 (class 1259 OID 34693)
-- Dependencies: 6
-- Name: ageing_entity_step; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE ageing_entity_step (
    id integer NOT NULL,
    entity_id integer,
    status_id integer,
    days integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.ageing_entity_step OWNER TO ibilling;

--
-- TOC entry 163 (class 1259 OID 34696)
-- Dependencies: 2253 2254 2255 6
-- Name: base_user; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE base_user (
    id integer NOT NULL,
    entity_id integer,
    password character varying(40),
    deleted smallint DEFAULT 0 NOT NULL,
    language_id integer,
    status_id integer,
    subscriber_status integer DEFAULT 1,
    currency_id integer,
    create_datetime timestamp without time zone NOT NULL,
    last_status_change timestamp without time zone,
    last_login timestamp without time zone,
    user_name character varying(50),
    failed_attempts integer DEFAULT 0 NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.base_user OWNER TO ibilling;

--
-- TOC entry 164 (class 1259 OID 34702)
-- Dependencies: 2256 6
-- Name: billing_process; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE billing_process (
    id integer NOT NULL,
    entity_id integer NOT NULL,
    billing_date date NOT NULL,
    period_unit_id integer NOT NULL,
    period_value integer NOT NULL,
    is_review integer NOT NULL,
    paper_invoice_batch_id integer,
    retries_to_do integer DEFAULT 0 NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.billing_process OWNER TO ibilling;

--
-- TOC entry 165 (class 1259 OID 34706)
-- Dependencies: 2257 2258 2259 2260 6
-- Name: billing_process_configuration; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE billing_process_configuration (
    id integer NOT NULL,
    entity_id integer,
    next_run_date date NOT NULL,
    generate_report smallint NOT NULL,
    retries integer,
    days_for_retry integer,
    days_for_report integer,
    review_status integer NOT NULL,
    period_unit_id integer NOT NULL,
    period_value integer NOT NULL,
    due_date_unit_id integer NOT NULL,
    due_date_value integer NOT NULL,
    df_fm smallint,
    only_recurring smallint DEFAULT 1 NOT NULL,
    invoice_date_process smallint NOT NULL,
    optlock integer NOT NULL,
    auto_payment smallint DEFAULT 0 NOT NULL,
    maximum_periods integer DEFAULT 1 NOT NULL,
    auto_payment_application integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.billing_process_configuration OWNER TO ibilling;

--
-- TOC entry 166 (class 1259 OID 34713)
-- Dependencies: 6
-- Name: blacklist; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE blacklist (
    id integer NOT NULL,
    entity_id integer NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    type integer NOT NULL,
    source integer NOT NULL,
    credit_card integer,
    credit_card_id integer,
    contact_id integer,
    user_id integer,
    optlock integer NOT NULL
);


ALTER TABLE public.blacklist OWNER TO ibilling;

--
-- TOC entry 167 (class 1259 OID 34716)
-- Dependencies: 6
-- Name: breadcrumb; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE breadcrumb (
    id integer NOT NULL,
    user_id integer NOT NULL,
    controller character varying(255) NOT NULL,
    action character varying(255),
    name character varying(255),
    object_id integer,
    version integer NOT NULL,
    description character varying(255)
);


ALTER TABLE public.breadcrumb OWNER TO ibilling;

--
-- TOC entry 168 (class 1259 OID 34722)
-- Dependencies: 6
-- Name: cdrentries; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE cdrentries (
    id integer NOT NULL,
    accountcode character varying(20),
    src character varying(20),
    dst character varying(20),
    dcontext character varying(20),
    clid character varying(20),
    channel character varying(20),
    dstchannel character varying(20),
    lastapp character varying(20),
    lastdatat character varying(20),
    start timestamp without time zone,
    answer timestamp without time zone,
    "end" timestamp without time zone,
    duration integer,
    billsec integer,
    disposition character varying(20),
    amaflags character varying(20),
    userfield character varying(100),
    ts timestamp without time zone
);


ALTER TABLE public.cdrentries OWNER TO ibilling;

--
-- TOC entry 169 (class 1259 OID 34725)
-- Dependencies: 2261 2262 6
-- Name: contact; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE contact (
    id integer NOT NULL,
    organization_name character varying(200),
    street_addres1 character varying(100),
    street_addres2 character varying(100),
    city character varying(50),
    state_province character varying(30),
    postal_code character varying(15),
    country_code character varying(2),
    last_name character varying(30),
    first_name character varying(30),
    person_initial character varying(5),
    person_title character varying(40),
    phone_country_code integer,
    phone_area_code integer,
    phone_phone_number character varying(20),
    fax_country_code integer,
    fax_area_code integer,
    fax_phone_number character varying(20),
    email character varying(200),
    create_datetime timestamp without time zone NOT NULL,
    deleted smallint DEFAULT 0 NOT NULL,
    notification_include smallint DEFAULT 1,
    user_id integer,
    optlock integer NOT NULL
);


ALTER TABLE public.contact OWNER TO ibilling;

--
-- TOC entry 170 (class 1259 OID 34733)
-- Dependencies: 6
-- Name: contact_field; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE contact_field (
    id integer NOT NULL,
    type_id integer,
    contact_id integer,
    content character varying(100) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.contact_field OWNER TO ibilling;

--
-- TOC entry 171 (class 1259 OID 34736)
-- Dependencies: 6
-- Name: contact_field_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE contact_field_type (
    id integer NOT NULL,
    entity_id integer,
    prompt_key character varying(50) NOT NULL,
    data_type character varying(10) NOT NULL,
    customer_readonly smallint,
    optlock integer NOT NULL
);


ALTER TABLE public.contact_field_type OWNER TO ibilling;

--
-- TOC entry 172 (class 1259 OID 34739)
-- Dependencies: 6
-- Name: contact_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE contact_map (
    id integer NOT NULL,
    contact_id integer,
    type_id integer NOT NULL,
    table_id integer NOT NULL,
    foreign_id integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.contact_map OWNER TO ibilling;

--
-- TOC entry 173 (class 1259 OID 34742)
-- Dependencies: 6
-- Name: contact_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE contact_type (
    id integer NOT NULL,
    entity_id integer,
    is_primary smallint,
    optlock integer NOT NULL
);


ALTER TABLE public.contact_type OWNER TO ibilling;

--
-- TOC entry 174 (class 1259 OID 34745)
-- Dependencies: 6
-- Name: country; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE country (
    id integer NOT NULL,
    code character varying(2) NOT NULL
);


ALTER TABLE public.country OWNER TO ibilling;

--
-- TOC entry 175 (class 1259 OID 34748)
-- Dependencies: 2263 6
-- Name: credit_card; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE credit_card (
    id integer NOT NULL,
    cc_number character varying(100) NOT NULL,
    cc_number_plain character varying(20),
    cc_expiry date NOT NULL,
    name character varying(150),
    cc_type integer NOT NULL,
    deleted smallint DEFAULT 0 NOT NULL,
    gateway_key character varying(100),
    optlock integer NOT NULL
);


ALTER TABLE public.credit_card OWNER TO ibilling;

--
-- TOC entry 176 (class 1259 OID 34752)
-- Dependencies: 6
-- Name: currency; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE currency (
    id integer NOT NULL,
    symbol character varying(10) NOT NULL,
    code character varying(3) NOT NULL,
    country_code character varying(2) NOT NULL,
    optlock integer
);


ALTER TABLE public.currency OWNER TO ibilling;

--
-- TOC entry 177 (class 1259 OID 34755)
-- Dependencies: 6
-- Name: currency_entity_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE currency_entity_map (
    currency_id integer,
    entity_id integer
);


ALTER TABLE public.currency_entity_map OWNER TO ibilling;

--
-- TOC entry 178 (class 1259 OID 34758)
-- Dependencies: 6
-- Name: currency_exchange; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE currency_exchange (
    id integer NOT NULL,
    entity_id integer,
    currency_id integer,
    rate numeric(22,10) NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.currency_exchange OWNER TO ibilling;

--
-- TOC entry 179 (class 1259 OID 34761)
-- Dependencies: 2264 6
-- Name: customer; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE customer (
    id integer NOT NULL,
    user_id integer,
    partner_id integer,
    referral_fee_paid smallint,
    invoice_delivery_method_id integer NOT NULL,
    notes character varying(1000),
    auto_payment_type integer,
    due_date_unit_id integer,
    due_date_value integer,
    df_fm smallint,
    parent_id integer,
    is_parent smallint,
    exclude_aging smallint DEFAULT 0 NOT NULL,
    invoice_child smallint,
    current_order_id integer,
    optlock integer NOT NULL,
    balance_type integer NOT NULL,
    dynamic_balance numeric(22,10),
    credit_limit numeric(22,10),
    auto_recharge numeric(22,10)
);


ALTER TABLE public.customer OWNER TO ibilling;

--
-- TOC entry 180 (class 1259 OID 34768)
-- Dependencies: 6
-- Name: entity; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE entity (
    id integer NOT NULL,
    external_id character varying(20),
    description character varying(100) NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    language_id integer NOT NULL,
    currency_id integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.entity OWNER TO ibilling;

--
-- TOC entry 181 (class 1259 OID 34771)
-- Dependencies: 6
-- Name: entity_delivery_method_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE entity_delivery_method_map (
    method_id integer,
    entity_id integer
);


ALTER TABLE public.entity_delivery_method_map OWNER TO ibilling;

--
-- TOC entry 182 (class 1259 OID 34774)
-- Dependencies: 6
-- Name: entity_payment_method_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE entity_payment_method_map (
    entity_id integer,
    payment_method_id integer
);


ALTER TABLE public.entity_payment_method_map OWNER TO ibilling;

--
-- TOC entry 183 (class 1259 OID 34777)
-- Dependencies: 6
-- Name: entity_report_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE entity_report_map (
    report_id integer NOT NULL,
    entity_id integer NOT NULL
);


ALTER TABLE public.entity_report_map OWNER TO ibilling;

--
-- TOC entry 184 (class 1259 OID 34780)
-- Dependencies: 6
-- Name: event_log; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE event_log (
    id integer NOT NULL,
    entity_id integer,
    user_id integer,
    table_id integer,
    foreign_id integer NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    level_field integer NOT NULL,
    module_id integer NOT NULL,
    message_id integer NOT NULL,
    old_num integer,
    old_str character varying(1000),
    old_date timestamp without time zone,
    optlock integer NOT NULL,
    affected_user_id integer
);


ALTER TABLE public.event_log OWNER TO ibilling;

--
-- TOC entry 185 (class 1259 OID 34786)
-- Dependencies: 6
-- Name: event_log_message; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE event_log_message (
    id integer NOT NULL
);


ALTER TABLE public.event_log_message OWNER TO ibilling;

--
-- TOC entry 186 (class 1259 OID 34789)
-- Dependencies: 6
-- Name: event_log_module; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE event_log_module (
    id integer NOT NULL
);


ALTER TABLE public.event_log_module OWNER TO ibilling;

--
-- TOC entry 187 (class 1259 OID 34792)
-- Dependencies: 6
-- Name: filter; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE filter (
    id integer NOT NULL,
    filter_set_id integer NOT NULL,
    type character varying(255) NOT NULL,
    constraint_type character varying(255) NOT NULL,
    field character varying(255) NOT NULL,
    template character varying(255) NOT NULL,
    visible boolean NOT NULL,
    integer_value integer,
    string_value character varying(255),
    start_date_value timestamp without time zone,
    end_date_value timestamp without time zone,
    version integer NOT NULL,
    boolean_value boolean,
    decimal_value numeric(22,10),
    decimal_high_value numeric(22,10)
);


ALTER TABLE public.filter OWNER TO ibilling;

--
-- TOC entry 188 (class 1259 OID 34798)
-- Dependencies: 6
-- Name: filter_set; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE filter_set (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    user_id integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.filter_set OWNER TO ibilling;

--
-- TOC entry 189 (class 1259 OID 34801)
-- Dependencies: 6
-- Name: filter_set_filter; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE filter_set_filter (
    filter_set_filters_id integer,
    filter_id integer
);


ALTER TABLE public.filter_set_filter OWNER TO ibilling;

--
-- TOC entry 190 (class 1259 OID 34804)
-- Dependencies: 6
-- Name: generic_status; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE generic_status (
    id integer NOT NULL,
    dtype character varying(50) NOT NULL,
    status_value integer NOT NULL,
    can_login smallint
);


ALTER TABLE public.generic_status OWNER TO ibilling;

--
-- TOC entry 191 (class 1259 OID 34807)
-- Dependencies: 6
-- Name: generic_status_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE generic_status_type (
    id character varying(50) NOT NULL
);


ALTER TABLE public.generic_status_type OWNER TO ibilling;

--
-- TOC entry 192 (class 1259 OID 34810)
-- Dependencies: 6
-- Name: international_description; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE international_description (
    table_id integer NOT NULL,
    foreign_id integer NOT NULL,
    psudo_column character varying(20) NOT NULL,
    language_id integer NOT NULL,
    content character varying(4000) NOT NULL
);


ALTER TABLE public.international_description OWNER TO ibilling;

--
-- TOC entry 193 (class 1259 OID 34816)
-- Dependencies: 2265 2266 2267 2268 6
-- Name: invoice; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE invoice (
    id integer NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    billing_process_id integer,
    user_id integer,
    delegated_invoice_id integer,
    due_date date NOT NULL,
    total numeric(22,10) NOT NULL,
    payment_attempts integer DEFAULT 0 NOT NULL,
    status_id smallint DEFAULT 1 NOT NULL,
    balance numeric(22,10),
    carried_balance numeric(22,10) NOT NULL,
    in_process_payment smallint DEFAULT 1 NOT NULL,
    is_review integer NOT NULL,
    currency_id integer NOT NULL,
    deleted smallint DEFAULT 0 NOT NULL,
    paper_invoice_batch_id integer,
    customer_notes character varying(1000),
    public_number character varying(40),
    last_reminder date,
    overdue_step integer,
    create_timestamp timestamp without time zone NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.invoice OWNER TO ibilling;

--
-- TOC entry 194 (class 1259 OID 34826)
-- Dependencies: 6
-- Name: invoice_delivery_method; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE invoice_delivery_method (
    id integer NOT NULL
);


ALTER TABLE public.invoice_delivery_method OWNER TO ibilling;

--
-- TOC entry 195 (class 1259 OID 34829)
-- Dependencies: 2269 2270 6
-- Name: invoice_line; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE invoice_line (
    id integer NOT NULL,
    invoice_id integer,
    type_id integer,
    amount numeric(22,10) NOT NULL,
    quantity numeric(22,10),
    price numeric(22,10),
    deleted smallint DEFAULT 0 NOT NULL,
    item_id integer,
    description character varying(1000),
    source_user_id integer,
    is_percentage smallint DEFAULT 0 NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.invoice_line OWNER TO ibilling;

--
-- TOC entry 196 (class 1259 OID 34837)
-- Dependencies: 6
-- Name: invoice_line_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE invoice_line_type (
    id integer NOT NULL,
    description character varying(50) NOT NULL,
    order_position integer NOT NULL
);


ALTER TABLE public.invoice_line_type OWNER TO ibilling;

--
-- TOC entry 197 (class 1259 OID 34840)
-- Dependencies: 2271 2272 6
-- Name: item; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE item (
    id integer NOT NULL,
    internal_number character varying(50),
    entity_id integer,
    percentage numeric(22,10),
    deleted smallint DEFAULT 0 NOT NULL,
    has_decimals smallint DEFAULT 0 NOT NULL,
    optlock integer NOT NULL,
    gl_code character varying(50),
    price_manual smallint NOT NULL
);


ALTER TABLE public.item OWNER TO ibilling;

--
-- TOC entry 198 (class 1259 OID 34845)
-- Dependencies: 6
-- Name: item_price; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE item_price (
    id integer NOT NULL,
    item_id integer,
    currency_id integer,
    price numeric(22,10) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.item_price OWNER TO ibilling;

--
-- TOC entry 199 (class 1259 OID 34848)
-- Dependencies: 6
-- Name: item_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE item_type (
    id integer NOT NULL,
    entity_id integer NOT NULL,
    description character varying(100),
    order_line_type_id integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.item_type OWNER TO ibilling;

--
-- TOC entry 200 (class 1259 OID 34851)
-- Dependencies: 6
-- Name: item_type_exclude_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE item_type_exclude_map (
    item_id integer NOT NULL,
    type_id integer NOT NULL
);


ALTER TABLE public.item_type_exclude_map OWNER TO ibilling;

--
-- TOC entry 201 (class 1259 OID 34854)
-- Dependencies: 6
-- Name: item_type_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE item_type_map (
    item_id integer,
    type_id integer
);


ALTER TABLE public.item_type_map OWNER TO ibilling;

--
-- TOC entry 238 (class 1259 OID 21247)
-- Dependencies: 6
-- Name: ibilling_constant; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE ibilling_constant (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    content character varying(100) NOT NULL
);


ALTER TABLE public.ibilling_constant OWNER TO ibilling;

--
-- TOC entry 202 (class 1259 OID 34857)
-- Dependencies: 6
-- Name: ibilling_seqs; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE ibilling_seqs (
    name character varying(255),
    next_id integer
);


ALTER TABLE public.ibilling_seqs OWNER TO ibilling;

--
-- TOC entry 203 (class 1259 OID 34860)
-- Dependencies: 6
-- Name: ibilling_table; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE ibilling_table (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.ibilling_table OWNER TO ibilling;

--
-- TOC entry 204 (class 1259 OID 34863)
-- Dependencies: 6
-- Name: language; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE language (
    id integer NOT NULL,
    code character varying(2) NOT NULL,
    description character varying(50) NOT NULL,
    country_code character varying(2) NOT NULL
);


ALTER TABLE public.language OWNER TO ibilling;

--
-- TOC entry 205 (class 1259 OID 34866)
-- Dependencies: 6
-- Name: mediation_cfg; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_cfg (
    id integer NOT NULL,
    entity_id integer NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    name character varying(50) NOT NULL,
    order_value integer NOT NULL,
    pluggable_task_id integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.mediation_cfg OWNER TO ibilling;

--
-- TOC entry 206 (class 1259 OID 34869)
-- Dependencies: 6
-- Name: mediation_errors; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_errors (
    accountcode character varying(50) NOT NULL,
    src character varying(50),
    dst character varying(50),
    dcontext character varying(50),
    clid character varying(50),
    channel character varying(50),
    dstchannel character varying(50),
    lastapp character varying(50),
    lastdata character varying(50),
    start timestamp without time zone,
    answer timestamp without time zone,
    "end" timestamp without time zone,
    duration integer,
    billsec integer,
    disposition character varying(50),
    amaflags character varying(50),
    userfield character varying(50),
    error_message character varying(200),
    should_retry boolean
);


ALTER TABLE public.mediation_errors OWNER TO ibilling;

--
-- TOC entry 207 (class 1259 OID 34875)
-- Dependencies: 6
-- Name: mediation_order_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_order_map (
    mediation_process_id integer NOT NULL,
    order_id integer NOT NULL
);


ALTER TABLE public.mediation_order_map OWNER TO ibilling;

--
-- TOC entry 208 (class 1259 OID 34878)
-- Dependencies: 6
-- Name: mediation_process; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_process (
    id integer NOT NULL,
    configuration_id integer NOT NULL,
    start_datetime timestamp without time zone NOT NULL,
    end_datetime timestamp without time zone,
    orders_affected integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.mediation_process OWNER TO ibilling;

--
-- TOC entry 209 (class 1259 OID 34881)
-- Dependencies: 6
-- Name: mediation_record; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_record (
    id_key character varying(100) NOT NULL,
    start_datetime timestamp without time zone NOT NULL,
    mediation_process_id integer,
    optlock integer NOT NULL,
    status_id integer NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.mediation_record OWNER TO ibilling;

--
-- TOC entry 210 (class 1259 OID 34884)
-- Dependencies: 6
-- Name: mediation_record_line; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE mediation_record_line (
    id integer NOT NULL,
    order_line_id integer NOT NULL,
    event_date timestamp without time zone NOT NULL,
    amount numeric(22,10) NOT NULL,
    quantity numeric(22,10) NOT NULL,
    description character varying(200),
    optlock integer NOT NULL,
    mediation_record_id integer NOT NULL
);


ALTER TABLE public.mediation_record_line OWNER TO ibilling;

--
-- TOC entry 211 (class 1259 OID 34887)
-- Dependencies: 6
-- Name: notification_category; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_category (
    id integer NOT NULL
);


ALTER TABLE public.notification_category OWNER TO ibilling;

--
-- TOC entry 212 (class 1259 OID 34890)
-- Dependencies: 2273 6
-- Name: notification_message; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message (
    id integer NOT NULL,
    type_id integer,
    entity_id integer NOT NULL,
    language_id integer NOT NULL,
    use_flag smallint DEFAULT 1 NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.notification_message OWNER TO ibilling;

--
-- TOC entry 213 (class 1259 OID 34894)
-- Dependencies: 6
-- Name: notification_message_arch; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message_arch (
    id integer NOT NULL,
    type_id integer,
    create_datetime timestamp without time zone NOT NULL,
    user_id integer,
    result_message character varying(200),
    optlock integer NOT NULL
);


ALTER TABLE public.notification_message_arch OWNER TO ibilling;

--
-- TOC entry 214 (class 1259 OID 34897)
-- Dependencies: 6
-- Name: notification_message_arch_line; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message_arch_line (
    id integer NOT NULL,
    message_archive_id integer,
    section integer NOT NULL,
    content character varying(1000) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.notification_message_arch_line OWNER TO ibilling;

--
-- TOC entry 215 (class 1259 OID 34903)
-- Dependencies: 6
-- Name: notification_message_line; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message_line (
    id integer NOT NULL,
    message_section_id integer,
    content character varying(1000) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.notification_message_line OWNER TO ibilling;

--
-- TOC entry 216 (class 1259 OID 34909)
-- Dependencies: 6
-- Name: notification_message_section; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message_section (
    id integer NOT NULL,
    message_id integer,
    section integer,
    optlock integer NOT NULL
);


ALTER TABLE public.notification_message_section OWNER TO ibilling;

--
-- TOC entry 217 (class 1259 OID 34912)
-- Dependencies: 6
-- Name: notification_message_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE notification_message_type (
    id integer NOT NULL,
    optlock integer NOT NULL,
    category_id integer
);


ALTER TABLE public.notification_message_type OWNER TO ibilling;

--
-- TOC entry 218 (class 1259 OID 34915)
-- Dependencies: 6
-- Name: order_billing_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE order_billing_type (
    id integer NOT NULL
);


ALTER TABLE public.order_billing_type OWNER TO ibilling;

--
-- TOC entry 219 (class 1259 OID 34918)
-- Dependencies: 2274 6
-- Name: order_line; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE order_line (
    id integer NOT NULL,
    order_id integer,
    item_id integer,
    type_id integer,
    amount numeric(22,10) NOT NULL,
    quantity numeric(22,10),
    price numeric(22,10),
    item_price smallint,
    create_datetime timestamp without time zone NOT NULL,
    deleted smallint DEFAULT 0 NOT NULL,
    description character varying(1000),
    provisioning_status integer,
    provisioning_request_id character varying(50),
    optlock integer NOT NULL,
    use_item boolean NOT NULL,
    group_id character(36)
);


ALTER TABLE public.order_line OWNER TO ibilling;

--
-- TOC entry 220 (class 1259 OID 34925)
-- Dependencies: 6
-- Name: order_line_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE order_line_type (
    id integer NOT NULL,
    editable smallint NOT NULL
);


ALTER TABLE public.order_line_type OWNER TO ibilling;

--
-- TOC entry 221 (class 1259 OID 34928)
-- Dependencies: 6
-- Name: order_period; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE order_period (
    id integer NOT NULL,
    entity_id integer,
    value integer,
    unit_id integer,
    optlock integer NOT NULL
);


ALTER TABLE public.order_period OWNER TO ibilling;

--
-- TOC entry 222 (class 1259 OID 34931)
-- Dependencies: 6
-- Name: order_process; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE order_process (
    id integer NOT NULL,
    order_id integer,
    invoice_id integer,
    billing_process_id integer,
    periods_included integer,
    period_start date,
    period_end date,
    is_review integer NOT NULL,
    origin integer,
    optlock integer NOT NULL
);


ALTER TABLE public.order_process OWNER TO ibilling;

--
-- TOC entry 223 (class 1259 OID 34934)
-- Dependencies: 6
-- Name: paper_invoice_batch; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE paper_invoice_batch (
    id integer NOT NULL,
    total_invoices integer NOT NULL,
    delivery_date date,
    is_self_managed smallint NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.paper_invoice_batch OWNER TO ibilling;

--
-- TOC entry 224 (class 1259 OID 34937)
-- Dependencies: 6
-- Name: partner; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE partner (
    id integer NOT NULL,
    user_id integer,
    balance numeric(22,10) NOT NULL,
    total_payments numeric(22,10) NOT NULL,
    total_refunds numeric(22,10) NOT NULL,
    total_payouts numeric(22,10) NOT NULL,
    percentage_rate numeric(22,10),
    referral_fee numeric(22,10),
    fee_currency_id integer,
    one_time smallint NOT NULL,
    period_unit_id integer NOT NULL,
    period_value integer NOT NULL,
    next_payout_date date NOT NULL,
    due_payout numeric(22,10),
    automatic_process smallint NOT NULL,
    related_clerk integer,
    optlock integer NOT NULL
);


ALTER TABLE public.partner OWNER TO ibilling;

--
-- TOC entry 225 (class 1259 OID 34940)
-- Dependencies: 6
-- Name: partner_payout; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE partner_payout (
    id integer NOT NULL,
    starting_date date NOT NULL,
    ending_date date NOT NULL,
    payments_amount numeric(22,10) NOT NULL,
    refunds_amount numeric(22,10) NOT NULL,
    balance_left numeric(22,10) NOT NULL,
    payment_id integer,
    partner_id integer,
    optlock integer NOT NULL
);


ALTER TABLE public.partner_payout OWNER TO ibilling;

--
-- TOC entry 226 (class 1259 OID 34943)
-- Dependencies: 6
-- Name: partner_range; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE partner_range (
    id integer NOT NULL,
    partner_id integer,
    percentage_rate numeric(22,10),
    referral_fee numeric(22,10),
    range_from integer NOT NULL,
    range_to integer NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.partner_range OWNER TO ibilling;

--
-- TOC entry 227 (class 1259 OID 34946)
-- Dependencies: 2275 2276 2277 6
-- Name: payment; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment (
    id integer NOT NULL,
    user_id integer,
    attempt integer,
    result_id integer,
    amount numeric(22,10) NOT NULL,
    create_datetime timestamp without time zone NOT NULL,
    update_datetime timestamp without time zone,
    payment_date date,
    method_id integer NOT NULL,
    credit_card_id integer,
    deleted smallint DEFAULT 0 NOT NULL,
    is_refund smallint DEFAULT 0 NOT NULL,
    is_preauth smallint DEFAULT 0 NOT NULL,
    payment_id integer,
    currency_id integer NOT NULL,
    payout_id integer,
    ach_id integer,
    balance numeric(22,10),
    optlock integer NOT NULL,
    payment_period integer,
    payment_notes character varying(500)
);


ALTER TABLE public.payment OWNER TO ibilling;

--
-- TOC entry 228 (class 1259 OID 34955)
-- Dependencies: 6
-- Name: payment_authorization; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment_authorization (
    id integer NOT NULL,
    payment_id integer,
    processor character varying(40) NOT NULL,
    code1 character varying(40) NOT NULL,
    code2 character varying(40),
    code3 character varying(40),
    approval_code character varying(20),
    avs character varying(20),
    transaction_id character varying(40),
    md5 character varying(100),
    card_code character varying(100),
    create_datetime date NOT NULL,
    response_message character varying(200),
    optlock integer NOT NULL
);


ALTER TABLE public.payment_authorization OWNER TO ibilling;

--
-- TOC entry 229 (class 1259 OID 34961)
-- Dependencies: 6
-- Name: payment_info_cheque; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment_info_cheque (
    id integer NOT NULL,
    payment_id integer,
    bank character varying(50),
    cheque_number character varying(50),
    cheque_date date,
    optlock integer NOT NULL
);


ALTER TABLE public.payment_info_cheque OWNER TO ibilling;

--
-- TOC entry 230 (class 1259 OID 34964)
-- Dependencies: 6
-- Name: payment_invoice; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment_invoice (
    id integer NOT NULL,
    payment_id integer,
    invoice_id integer,
    amount numeric(22,10),
    create_datetime timestamp without time zone NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.payment_invoice OWNER TO ibilling;

--
-- TOC entry 231 (class 1259 OID 34967)
-- Dependencies: 6
-- Name: payment_method; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment_method (
    id integer NOT NULL
);


ALTER TABLE public.payment_method OWNER TO ibilling;

--
-- TOC entry 232 (class 1259 OID 34970)
-- Dependencies: 6
-- Name: payment_result; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE payment_result (
    id integer NOT NULL
);


ALTER TABLE public.payment_result OWNER TO ibilling;

--
-- TOC entry 233 (class 1259 OID 34973)
-- Dependencies: 6
-- Name: period_unit; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE period_unit (
    id integer NOT NULL
);


ALTER TABLE public.period_unit OWNER TO ibilling;

--
-- TOC entry 234 (class 1259 OID 34976)
-- Dependencies: 6
-- Name: permission; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE permission (
    id integer NOT NULL,
    type_id integer NOT NULL,
    foreign_id integer
);


ALTER TABLE public.permission OWNER TO ibilling;

--
-- TOC entry 235 (class 1259 OID 34979)
-- Dependencies: 6
-- Name: permission_role_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE permission_role_map (
    permission_id integer,
    role_id integer
);


ALTER TABLE public.permission_role_map OWNER TO ibilling;

--
-- TOC entry 236 (class 1259 OID 34982)
-- Dependencies: 6
-- Name: permission_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE permission_type (
    id integer NOT NULL,
    description character varying(30) NOT NULL
);


ALTER TABLE public.permission_type OWNER TO ibilling;

--
-- TOC entry 237 (class 1259 OID 34985)
-- Dependencies: 6
-- Name: permission_user; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE permission_user (
    permission_id integer,
    user_id integer,
    is_grant smallint NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.permission_user OWNER TO ibilling;

--
-- TOC entry 238 (class 1259 OID 34988)
-- Dependencies: 6
-- Name: pluggable_task; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE pluggable_task (
    id integer NOT NULL,
    entity_id integer NOT NULL,
    type_id integer,
    processing_order integer NOT NULL,
    optlock integer NOT NULL,
    notes character varying(1000)
);


ALTER TABLE public.pluggable_task OWNER TO ibilling;

--
-- TOC entry 239 (class 1259 OID 34994)
-- Dependencies: 6
-- Name: pluggable_task_parameter; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE pluggable_task_parameter (
    id integer NOT NULL,
    task_id integer,
    name character varying(50) NOT NULL,
    int_value integer,
    str_value character varying(500),
    float_value numeric(22,10),
    optlock integer NOT NULL
);


ALTER TABLE public.pluggable_task_parameter OWNER TO ibilling;

--
-- TOC entry 240 (class 1259 OID 35000)
-- Dependencies: 6
-- Name: pluggable_task_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE pluggable_task_type (
    id integer NOT NULL,
    category_id integer NOT NULL,
    class_name character varying(200) NOT NULL,
    min_parameters integer NOT NULL
);


ALTER TABLE public.pluggable_task_type OWNER TO ibilling;

--
-- TOC entry 241 (class 1259 OID 35003)
-- Dependencies: 6
-- Name: pluggable_task_type_category; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE pluggable_task_type_category (
    id integer NOT NULL,
    interface_name character varying(200) NOT NULL
);


ALTER TABLE public.pluggable_task_type_category OWNER TO ibilling;

--
-- TOC entry 242 (class 1259 OID 35006)
-- Dependencies: 6
-- Name: preference; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE preference (
    id integer NOT NULL,
    type_id integer,
    table_id integer NOT NULL,
    foreign_id integer NOT NULL,
    value character varying(200)
);


ALTER TABLE public.preference OWNER TO ibilling;

--
-- TOC entry 243 (class 1259 OID 35009)
-- Dependencies: 6
-- Name: preference_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE preference_type (
    id integer NOT NULL,
    def_value character varying(200)
);


ALTER TABLE public.preference_type OWNER TO ibilling;

--
-- TOC entry 244 (class 1259 OID 35012)
-- Dependencies: 6
-- Name: process_run; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE process_run (
    id integer NOT NULL,
    process_id integer,
    run_date date NOT NULL,
    started timestamp without time zone NOT NULL,
    finished timestamp without time zone,
    payment_finished timestamp without time zone,
    invoices_generated integer,
    optlock integer NOT NULL,
    status_id integer NOT NULL
);


ALTER TABLE public.process_run OWNER TO ibilling;

--
-- TOC entry 245 (class 1259 OID 35015)
-- Dependencies: 6
-- Name: process_run_total; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE process_run_total (
    id integer NOT NULL,
    process_run_id integer,
    currency_id integer NOT NULL,
    total_invoiced numeric(22,10),
    total_paid numeric(22,10),
    total_not_paid numeric(22,10),
    optlock integer NOT NULL
);


ALTER TABLE public.process_run_total OWNER TO ibilling;

--
-- TOC entry 246 (class 1259 OID 35018)
-- Dependencies: 6
-- Name: process_run_total_pm; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE process_run_total_pm (
    id integer NOT NULL,
    process_run_total_id integer,
    payment_method_id integer,
    total numeric(22,10) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.process_run_total_pm OWNER TO ibilling;

--
-- TOC entry 247 (class 1259 OID 35021)
-- Dependencies: 2278 6
-- Name: process_run_user; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE process_run_user (
    id integer NOT NULL,
    process_run_id integer NOT NULL,
    user_id integer NOT NULL,
    status integer NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.process_run_user OWNER TO ibilling;

--
-- TOC entry 248 (class 1259 OID 35025)
-- Dependencies: 6
-- Name: promotion; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE promotion (
    id integer NOT NULL,
    item_id integer,
    code character varying(50) NOT NULL,
    notes character varying(200),
    once smallint NOT NULL,
    since date,
    until date
);


ALTER TABLE public.promotion OWNER TO ibilling;

--
-- TOC entry 249 (class 1259 OID 35028)
-- Dependencies: 6
-- Name: promotion_user_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE promotion_user_map (
    user_id integer,
    promotion_id integer
);


ALTER TABLE public.promotion_user_map OWNER TO ibilling;

--
-- TOC entry 250 (class 1259 OID 35031)
-- Dependencies: 2279 6
-- Name: purchase_order; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE purchase_order (
    id integer NOT NULL,
    user_id integer,
    period_id integer,
    billing_type_id integer NOT NULL,
    active_since date,
    active_until date,
    cycle_start date,
    create_datetime timestamp without time zone NOT NULL,
    next_billable_day date,
    created_by integer,
    status_id integer NOT NULL,
    currency_id integer NOT NULL,
    deleted smallint DEFAULT 0 NOT NULL,
    excludeFromBp smallint DEFAULT 0 NOT NULL,
    notify smallint,
    last_notified timestamp without time zone,
    notification_step integer,
    due_date_unit_id integer,
    due_date_value integer,
    df_fm smallint,
    anticipate_periods integer,
    own_invoice smallint,
    notes character varying(200),
    notes_in_invoice smallint,
    is_current smallint,
    optlock integer NOT NULL,
    parent_order integer
);


ALTER TABLE public.purchase_order OWNER TO ibilling;

--
-- TOC entry 251 (class 1259 OID 35035)
-- Dependencies: 6
-- Name: recent_item; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE recent_item (
    id integer NOT NULL,
    type character varying(255) NOT NULL,
    object_id integer NOT NULL,
    user_id integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.recent_item OWNER TO ibilling;

--
-- TOC entry 252 (class 1259 OID 35038)
-- Dependencies: 6
-- Name: report; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE report (
    id integer NOT NULL,
    type_id integer NOT NULL,
    name character varying(255) NOT NULL,
    file_name character varying(500) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.report OWNER TO ibilling;

--
-- TOC entry 253 (class 1259 OID 35044)
-- Dependencies: 6
-- Name: report_parameter; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE report_parameter (
    id integer NOT NULL,
    report_id integer NOT NULL,
    dtype character varying(10) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.report_parameter OWNER TO ibilling;

--
-- TOC entry 254 (class 1259 OID 35047)
-- Dependencies: 6
-- Name: report_type; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE report_type (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    optlock integer NOT NULL
);


ALTER TABLE public.report_type OWNER TO ibilling;

--
-- TOC entry 255 (class 1259 OID 35050)
-- Dependencies: 6
-- Name: role; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE role (
    id integer NOT NULL,
    entity_id integer,
    role_type_id integer
);


ALTER TABLE public.role OWNER TO ibilling;

--
-- TOC entry 256 (class 1259 OID 35053)
-- Dependencies: 6
-- Name: shortcut; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE shortcut (
    id integer NOT NULL,
    user_id integer NOT NULL,
    controller character varying(255) NOT NULL,
    action character varying(255),
    name character varying(255),
    object_id integer,
    version integer NOT NULL
);


ALTER TABLE public.shortcut OWNER TO ibilling;

--
-- TOC entry 257 (class 1259 OID 35059)
-- Dependencies: 6
-- Name: user_credit_card_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE user_credit_card_map (
    user_id integer,
    credit_card_id integer
);


ALTER TABLE public.user_credit_card_map OWNER TO ibilling;

--
-- TOC entry 258 (class 1259 OID 35062)
-- Dependencies: 6
-- Name: user_role_map; Type: TABLE; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE TABLE user_role_map (
    user_id integer,
    role_id integer
);


ALTER TABLE public.user_role_map OWNER TO ibilling;

--
-- TOC entry 2631 (class 0 OID 34689)
-- Dependencies: 161
-- Data for Name: ach; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY ach (id, user_id, aba_routing, bank_account, account_type, bank_name, account_name, optlock, gateway_key) FROM stdin;
\.


--
-- TOC entry 2632 (class 0 OID 34693)
-- Dependencies: 162
-- Data for Name: ageing_entity_step; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY ageing_entity_step (id, entity_id, status_id, days, optlock) FROM stdin;
1	1	1	5	1
3	1	2	3	1
4	1	3	1	1
5	1	5	2	1
6	1	7	30	1
7	1	8	0	1
\.


--
-- TOC entry 2633 (class 0 OID 34696)
-- Dependencies: 163
-- Data for Name: base_user; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY base_user (id, entity_id, password, deleted, language_id, status_id, subscriber_status, currency_id, create_datetime, last_status_change, last_login, user_name, failed_attempts, optlock) FROM stdin;
1	1	46f94c8de14fb36680850768ff1b7f2a	0	1	1	9	1	2007-03-18 00:00:00	\N	2010-05-25 12:27:12.217	admin	0	18
10801	1	25d55ad283aa400af464c76d713c07ad	0	1	1	9	1	2012-07-13 10:46:42.362	\N	\N	crystal	0	1
\.


--
-- TOC entry 2634 (class 0 OID 34702)
-- Dependencies: 164
-- Data for Name: billing_process; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY billing_process (id, entity_id, billing_date, period_unit_id, period_value, is_review, paper_invoice_batch_id, retries_to_do, optlock) FROM stdin;
2	1	2006-08-26	1	1	0	\N	1	1
12	1	2006-09-26	1	1	0	\N	0	1
\.


--
-- TOC entry 2635 (class 0 OID 34706)
-- Dependencies: 165
-- Data for Name: billing_process_configuration; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY billing_process_configuration (id, entity_id, next_run_date, generate_report, retries, days_for_retry, days_for_report, review_status, period_unit_id, period_value, due_date_unit_id, due_date_value, df_fm, only_recurring, invoice_date_process, optlock, auto_payment, maximum_periods, auto_payment_application) FROM stdin;
1	1	2006-10-26	1	0	1	3	1	1	1	1	1	0	1	0	1	0	1	1
\.


--
-- TOC entry 2636 (class 0 OID 34713)
-- Dependencies: 166
-- Data for Name: blacklist; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY blacklist (id, entity_id, create_datetime, type, source, credit_card, credit_card_id, contact_id, user_id, optlock) FROM stdin;
2	1	2008-09-26 00:00:00	2	2	\N	\N	1125	\N	1
3	1	2008-09-26 00:00:00	3	2	\N	1013	\N	\N	1
4	1	2008-09-26 00:00:00	4	2	\N	\N	1126	\N	1
5	1	2008-09-26 00:00:00	5	2	\N	\N	1128	\N	1
6	1	2008-09-26 00:00:00	6	2	\N	\N	1127	\N	1
\.


--
-- TOC entry 2637 (class 0 OID 34716)
-- Dependencies: 167
-- Data for Name: breadcrumb; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY breadcrumb (id, user_id, controller, action, name, object_id, version, description) FROM stdin;
88	1	customer	list	\N	10801	0	crystal
89	1	order	list	\N	\N	0	\N
90	1	customer	list	\N	\N	0	\N
91	1	customer	list	\N	10801	0	crystal
92	1	product	list	\N	\N	0	\N
93	1	customer	list	\N	\N	0	\N
94	1	customer	list	\N	10801	0	crystal
\.


--
-- TOC entry 2638 (class 0 OID 34722)
-- Dependencies: 168
-- Data for Name: cdrentries; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY cdrentries (id, accountcode, src, dst, dcontext, clid, channel, dstchannel, lastapp, lastdatat, start, answer, "end", duration, billsec, disposition, amaflags, userfield, ts) FROM stdin;
1	20121	4033211001	4501231533	jb-test-ctx	Filler Events <1234>	IAX2/0282119604-13	SIP/8315-b791bcc0	Dial	dial data	2007-11-17 11:09:01	2007-11-17 11:09:59	2007-11-17 11:27:31	200	12000	ANSWERED	3	mediation-batch-test-13	\N
\.


--
-- TOC entry 2639 (class 0 OID 34725)
-- Dependencies: 169
-- Data for Name: contact; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY contact (id, organization_name, street_addres1, street_addres2, city, state_province, postal_code, country_code, last_name, first_name, person_initial, person_title, phone_country_code, phone_area_code, phone_phone_number, fax_country_code, fax_area_code, fax_phone_number, email, create_datetime, deleted, notification_include, user_id, optlock) FROM stdin;
1	Prancing Pony	1234 Great East Road		Bree	Middle Earth	54321	CN	\N	\N	\N	\N	\N	123	321-1234	\N	\N	\N	admin@prancingpony.me	2007-03-18 00:00:00	0	1	\N	1
2	Prancing Pony	1234 Great East Road		Bree	Middle Earth	54321	CN	Strator	Admin	\N	\N	\N	123	321-1234	\N	\N	\N	admin@prancingpony.me	2007-03-18 00:00:00	0	1	1	1
113201							CN			\N	\N	\N	\N		\N	\N	\N	support@21viacloud.com	2012-07-13 10:46:42.382	0	0	10801	1
\.


--
-- TOC entry 2640 (class 0 OID 34733)
-- Dependencies: 170
-- Data for Name: contact_field; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY contact_field (id, type_id, contact_id, content, optlock) FROM stdin;
\.


--
-- TOC entry 2641 (class 0 OID 34736)
-- Dependencies: 171
-- Data for Name: contact_field_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY contact_field_type (id, entity_id, prompt_key, data_type, customer_readonly, optlock) FROM stdin;
\.


--
-- TOC entry 2642 (class 0 OID 34739)
-- Dependencies: 172
-- Data for Name: contact_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY contact_map (id, contact_id, type_id, table_id, foreign_id, optlock) FROM stdin;
6780	1	1	5	1	1
6781	2	2	10	1	1
791101	113201	2	10	10801	1
\.


--
-- TOC entry 2643 (class 0 OID 34742)
-- Dependencies: 173
-- Data for Name: contact_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY contact_type (id, entity_id, is_primary, optlock) FROM stdin;
1	\N	\N	0
2	1	1	0
4	1	0	0
\.


--
-- TOC entry 2644 (class 0 OID 34745)
-- Dependencies: 174
-- Data for Name: country; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY country (id, code) FROM stdin;
1	AF
2	AL
3	DZ
4	AS
5	AD
6	AO
7	AI
8	AQ
9	AG
10	AR
11	AM
12	AW
13	AU
14	AT
15	AZ
16	BS
17	BH
18	BD
19	BB
20	BY
21	BE
22	BZ
23	BJ
24	BM
25	BT
26	BO
27	BA
28	BW
29	BV
30	BR
31	IO
32	BN
33	BG
34	BF
35	BI
36	KH
37	CM
38	CA
39	CV
40	KY
41	CF
42	TD
43	CL
44	CN
45	CX
46	CC
47	CO
48	KM
49	CG
50	CK
51	CR
52	CI
53	HR
54	CU
55	CY
56	CZ
57	CD
58	DK
59	DJ
60	DM
61	DO
62	TP
63	EC
64	EG
65	SV
66	GQ
67	ER
68	EE
69	ET
70	FK
71	FO
72	FJ
73	FI
74	FR
75	GF
76	PF
77	TF
78	GA
79	GM
80	GE
81	DE
82	GH
83	GI
84	GR
85	GL
86	GD
87	GP
88	GU
89	GT
90	GN
91	GW
92	GY
93	HT
94	HM
95	HN
96	HK
97	HU
98	IS
99	IN
100	ID
101	IR
102	IQ
103	IE
104	IL
105	IT
106	JM
107	JP
108	JO
109	KZ
110	KE
111	KI
112	KR
113	KW
114	KG
115	LA
116	LV
117	LB
118	LS
119	LR
120	LY
121	LI
122	LT
123	LU
124	MO
125	MK
126	MG
127	MW
128	MY
129	MV
130	ML
131	MT
132	MH
133	MQ
134	MR
135	MU
136	YT
137	MX
138	FM
139	MD
140	MC
141	MN
142	MS
143	MA
144	MZ
145	MM
146	NA
147	NR
148	NP
149	NL
150	AN
151	NC
152	NZ
153	NI
154	NE
155	NG
156	NU
157	NF
158	KP
159	MP
160	NO
161	OM
162	PK
163	PW
164	PA
165	PG
166	PY
167	PE
168	PH
169	PN
170	PL
171	PT
172	PR
173	QA
174	RE
175	RO
176	RU
177	RW
178	WS
179	SM
180	ST
181	SA
182	SN
183	YU
184	SC
185	SL
186	SG
187	SK
188	SI
189	SB
190	SO
191	ZA
192	GS
193	ES
194	LK
195	SH
196	KN
197	LC
198	PM
199	VC
200	SD
201	SR
202	SJ
203	SZ
204	SE
205	CH
206	SY
207	TW
208	TJ
209	TZ
210	TH
211	TG
212	TK
213	TO
214	TT
215	TN
216	TR
217	TM
218	TC
219	TV
220	UG
221	UA
222	AE
223	UK
224	US
225	UM
226	UY
227	UZ
228	VU
229	VA
230	VE
231	VN
232	VG
233	VI
234	WF
235	YE
236	ZM
237	ZW
\.


--
-- TOC entry 2645 (class 0 OID 34748)
-- Dependencies: 175
-- Data for Name: credit_card; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY credit_card (id, cc_number, cc_number_plain, cc_expiry, name, cc_type, deleted, gateway_key, optlock) FROM stdin;
\.


--
-- TOC entry 2646 (class 0 OID 34752)
-- Dependencies: 176
-- Data for Name: currency; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY currency (id, symbol, code, country_code, optlock) FROM stdin;
1	¥	RMB	CN	0
2	US$	USD	US	0
\.


--
-- TOC entry 2647 (class 0 OID 34755)
-- Dependencies: 177
-- Data for Name: currency_entity_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY currency_entity_map (currency_id, entity_id) FROM stdin;
1	1
2	1
\.


--
-- TOC entry 2648 (class 0 OID 34758)
-- Dependencies: 178
-- Data for Name: currency_exchange; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY currency_exchange (id, entity_id, currency_id, rate, create_datetime, optlock) FROM stdin;
1	0	2	1.3250000000	2004-03-09 00:00:00	1
\.


--
-- TOC entry 2649 (class 0 OID 34761)
-- Dependencies: 179
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY customer (id, user_id, partner_id, referral_fee_paid, invoice_delivery_method_id, notes, auto_payment_type, due_date_unit_id, due_date_value, df_fm, parent_id, is_parent, exclude_aging, invoice_child, current_order_id, optlock, balance_type, dynamic_balance, credit_limit, auto_recharge) FROM stdin;
107101	10801	\N	\N	1		1	3	\N	\N	\N	0	0	\N	\N	1	1	0.0000000000	0.0000000000	0.0000000000
\.


--
-- TOC entry 2650 (class 0 OID 34768)
-- Dependencies: 180
-- Data for Name: entity; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY entity (id, external_id, description, create_datetime, language_id, currency_id, optlock) FROM stdin;
1	\N	上海世纪互联	2007-03-18 00:00:00	1	1	1
\.


--
-- TOC entry 2651 (class 0 OID 34771)
-- Dependencies: 181
-- Data for Name: entity_delivery_method_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY entity_delivery_method_map (method_id, entity_id) FROM stdin;
1	1
2	1
3	1
\.


--
-- TOC entry 2652 (class 0 OID 34774)
-- Dependencies: 182
-- Data for Name: entity_payment_method_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY entity_payment_method_map (entity_id, payment_method_id) FROM stdin;
1	1
1	2
1	3
\.


--
-- TOC entry 2653 (class 0 OID 34777)
-- Dependencies: 183
-- Data for Name: entity_report_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY entity_report_map (report_id, entity_id) FROM stdin;
\.


--
-- TOC entry 2654 (class 0 OID 34780)
-- Dependencies: 184
-- Data for Name: event_log; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY event_log (id, entity_id, user_id, table_id, foreign_id, create_datetime, level_field, module_id, message_id, old_num, old_str, old_date, optlock, affected_user_id) FROM stdin;
470004	1	\N	10	10801	2012-07-13 10:46:42.37	2	2	25	\N	\N	\N	0	10801
470005	\N	\N	27	113201	2012-07-13 10:46:42.4	2	2	25	\N	\N	\N	0	\N
470006	1	\N	27	113201	2012-07-13 10:46:42.61	2	2	9	\N	\N	\N	0	10801
\.


--
-- TOC entry 2655 (class 0 OID 34786)
-- Dependencies: 185
-- Data for Name: event_log_message; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY event_log_message (id) FROM stdin;
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
\.


--
-- TOC entry 2656 (class 0 OID 34789)
-- Dependencies: 186
-- Data for Name: event_log_module; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY event_log_module (id) FROM stdin;
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
\.


--
-- TOC entry 2657 (class 0 OID 34792)
-- Dependencies: 187
-- Data for Name: filter; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY filter (id, filter_set_id, type, constraint_type, field, template, visible, integer_value, string_value, start_date_value, end_date_value, version, boolean_value, decimal_value, decimal_high_value) FROM stdin;
\.


--
-- TOC entry 2658 (class 0 OID 34798)
-- Dependencies: 188
-- Data for Name: filter_set; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY filter_set (id, name, user_id, version) FROM stdin;
\.


--
-- TOC entry 2659 (class 0 OID 34801)
-- Dependencies: 189
-- Data for Name: filter_set_filter; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY filter_set_filter (filter_set_filters_id, filter_id) FROM stdin;
\.


--
-- TOC entry 2660 (class 0 OID 34804)
-- Dependencies: 190
-- Data for Name: generic_status; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY generic_status (id, dtype, status_value, can_login) FROM stdin;
1	user_status	1	1
2	user_status	2	1
3	user_status	3	1
4	user_status	4	1
5	user_status	5	0
6	user_status	6	0
7	user_status	7	0
8	user_status	8	0
9	subscriber_status	1	\N
10	subscriber_status	2	\N
11	subscriber_status	3	\N
12	subscriber_status	4	\N
13	subscriber_status	5	\N
14	subscriber_status	6	\N
15	subscriber_status	7	\N
16	order_status	1	\N
17	order_status	2	\N
18	order_status	3	\N
19	order_status	4	\N
20	order_line_provisioning_status	1	\N
21	order_line_provisioning_status	2	\N
22	order_line_provisioning_status	3	\N
23	order_line_provisioning_status	4	\N
24	order_line_provisioning_status	5	\N
25	order_line_provisioning_status	6	\N
26	invoice_status	1	\N
27	invoice_status	2	\N
28	invoice_status	3	\N
29	mediation_record_status	1	\N
30	mediation_record_status	2	\N
31	mediation_record_status	3	\N
32	mediation_record_status	4	\N
33	process_run_status	1	\N
34	process_run_status	2	\N
35	process_run_status	3	\N
\.


--
-- TOC entry 2661 (class 0 OID 34807)
-- Dependencies: 191
-- Data for Name: generic_status_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY generic_status_type (id) FROM stdin;
order_line_provisioning_status
order_status
subscriber_status
user_status
invoice_status
mediation_record_status
process_run_status
\.


--
-- TOC entry 2662 (class 0 OID 34810)
-- Dependencies: 192
-- Data for Name: international_description; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY international_description (table_id, foreign_id, psudo_column, language_id, content) FROM stdin;
4	1	description	1	人民币
4	2	description	1	美元
4	3	description	1	Euro
4	4	description	1	Yen
4	5	description	1	Pound Sterling
4	6	description	1	Won
4	7	description	1	Swiss Franc
4	8	description	1	Swedish Krona
4	9	description	1	Singapore Dollar
4	10	description	1	Malaysian Ringgit
4	11	description	1	Australian Dollar
6	1	description	1	Month
6	2	description	1	Week
6	3	description	1	Day
6	4	description	1	Year
7	1	description	1	Email
7	2	description	1	Paper
7	3	description	1	Email + Paper
9	1	description	1	Active
9	2	description	1	Overdue
9	3	description	1	Overdue 2
9	4	description	1	Overdue 3
9	5	description	1	Suspended
9	6	description	1	Suspended 2
9	7	description	1	Suspended 3
9	8	description	1	Deleted
14	1	description	1	Lemonade - 1 per day monthly pass
14	2	description	1	Lemonade - all you can drink monthly
14	3	description	1	Coffee - one per day - Monthly
14	4	description	1	Poison Ivy juice (cold)
14	14	description	1	10% Elf discount.
14	24	description	1	Cancel fee
14	240	description	1	Currency test item
14	250	description	1	Lemonade Plan
14	251	description	1	Lemonade plan - Setup Fee
14	270	description	1	Late payment penalty fee
14	260	description	1	an item from ws
17	1	description	1	One time
17	2	description	1	Monthly
17	3	description	1	Monthly
18	1	description	1	Items
18	2	description	1	Tax
18	3	description	1	Penalty
19	1	description	1	pre paid
19	2	description	1	post paid
20	1	description	1	Active
20	2	description	1	Finished
20	3	description	1	Suspended
20	4	description	1	Suspended (auto)
28	2	description	1	Primary
28	3	description	1	Primary
28	4	description	1	Extra Contact
35	1	description	1	Cheque
35	2	description	1	Visa
35	3	description	1	MasterCard
35	4	description	1	AMEX
35	5	description	1	ACH
35	6	description	1	Discovery
35	7	description	1	Diners
35	8	description	1	PayPal
35	9	description	1	Payment Gateway Key
41	1	description	1	Successful
41	2	description	1	Failed
41	3	description	1	Processor unavailable
41	4	description	1	Entered
46	1	description	1	Billing Process
46	2	description	1	User maintenance
46	3	description	1	Item maintenance
46	4	description	1	Item type maintenance
46	5	description	1	Item user price maintenance
46	6	description	1	Promotion maintenance
46	7	description	1	Order maintenance
46	8	description	1	Credit card maintenance
46	9	description	1	Invoice maintenance
47	1	description	1	A prepaid order has unbilled time before the billing process date
47	2	description	1	Order has no active time at the date of process.
47	3	description	1	At least one complete period has to be billable.
47	4	description	1	Already billed for the current date.
47	5	description	1	This order had to be maked for exclusion in the last process.
47	6	description	1	Pre-paid order is being process after its expiration.
47	7	description	1	A row was marked as deleted.
47	8	description	1	A user password was changed.
47	9	description	1	A row was updated.
47	10	description	1	Running a billing process, but a review is found unapproved.
47	11	description	1	Running a billing process, review is required but not present.
47	12	description	1	A user status was changed.
47	13	description	1	An order status was changed.
47	14	description	1	A user had to be aged, but there are no more steps configured.
47	15	description	1	A partner has a payout ready, but no payment instrument.
47	16	description	1	A purchase order as been manually applied to an invoice.
50	4	description	1	Grace period
50	5	description	1	Partner percentage rate
50	6	description	1	Partner referral fee
50	7	description	1	Partner one time payout
50	8	description	1	Partner period unit payout
50	9	description	1	Partner period value payout
50	10	description	1	Partner automatic payout
50	11	description	1	User in charge of partners 
50	12	description	1	Partner fee currency
50	13	description	1	Self delivery of paper invoices
50	14	description	1	Include customer notes in invoice
50	15	description	1	Days before expiration for order notification
50	16	description	1	Days before expiration for order notification 2
50	17	description	1	Days before expiration for order notification 3
50	18	description	1	Invoice number prefix
50	19	description	1	Next invoice number
50	20	description	1	Manual invoice deletion
50	21	description	1	Use invoice reminders
50	22	description	1	Number of days after the invoice generation for the first reminder
50	23	description	1	Number of days for next reminder
50	24	description	1	Data Fattura Fine Mese
52	1	description	1	Invoice (email)
52	2	description	1	User Reactivated
52	3	description	1	User Overdue
52	4	description	1	User Overdue 2
52	5	description	1	User Overdue 3
52	6	description	1	User Suspended
52	7	description	1	User Suspended 2
52	8	description	1	User Suspended 3
52	9	description	1	User Deleted
52	10	description	1	Payout Remainder
52	11	description	1	Partner Payout
52	12	description	1	Invoice (paper)
52	13	description	1	Order about to expire. Step 1
52	14	description	1	Order about to expire. Step 2
52	15	description	1	Order about to expire. Step 3
52	16	description	1	Payment (successful)
52	17	description	1	Payment (failed)
52	18	description	1	Invoice Reminder
52	19	description	1	Update Credit Card
52	20	description	1	Lost password
60	1	description	1	An internal user with all the permissions
60	1	title	1	Internal
60	2	description	1	The super user of an entity
60	2	title	1	Super user
60	3	description	1	A billing clerk
60	3	title	1	Clerk
60	4	description	1	A partner that will bring customers
60	4	title	1	Partner
60	5	description	1	A customer that will query his/her account
60	5	title	1	Customer
64	1	description	1	Afghanistan
64	2	description	1	Albania
64	3	description	1	Algeria
64	4	description	1	American Samoa
64	5	description	1	Andorra
64	6	description	1	Angola
64	7	description	1	Anguilla
64	8	description	1	Antarctica
64	9	description	1	Antigua and Barbuda
64	10	description	1	Argentina
64	11	description	1	Armenia
64	12	description	1	Aruba
64	13	description	1	Australia
64	14	description	1	Austria
64	15	description	1	Azerbaijan
64	16	description	1	Bahamas
64	17	description	1	Bahrain
64	18	description	1	Bangladesh
64	19	description	1	Barbados
64	20	description	1	Belarus
64	21	description	1	Belgium
64	22	description	1	Belize
64	23	description	1	Benin
64	24	description	1	Bermuda
64	25	description	1	Bhutan
64	26	description	1	Bolivia
64	27	description	1	Bosnia and Herzegovina
64	28	description	1	Botswana
64	29	description	1	Bouvet Island
64	30	description	1	Brazil
64	31	description	1	British Indian Ocean Territory
64	32	description	1	Brunei
64	33	description	1	Bulgaria
64	34	description	1	Burkina Faso
64	35	description	1	Burundi
64	36	description	1	Cambodia
64	37	description	1	Cameroon
64	38	description	1	Canada
64	39	description	1	Cape Verde
64	40	description	1	Cayman Islands
64	41	description	1	Central African Republic
64	42	description	1	Chad
64	43	description	1	Chile
64	44	description	1	China
64	45	description	1	Christmas Island
64	46	description	1	Cocos &#40;Keeling&#41; Islands
64	47	description	1	Colombia
64	48	description	1	Comoros
64	49	description	1	Congo
64	50	description	1	Cook Islands
64	51	description	1	Costa Rica
64	52	description	1	Cï¿½1ï¿½7te d&#39;Ivoire
64	53	description	1	Croatia &#40;Hrvatska&#41;
64	54	description	1	Cuba
64	55	description	1	Cyprus
64	56	description	1	Czech Republic
64	57	description	1	Congo &#40;DRC&#41;
64	58	description	1	Denmark
64	59	description	1	Djibouti
64	60	description	1	Dominica
64	61	description	1	Dominican Republic
64	62	description	1	East Timor
64	63	description	1	Ecuador
64	64	description	1	Egypt
64	65	description	1	El Salvador
64	66	description	1	Equatorial Guinea
64	67	description	1	Eritrea
64	68	description	1	Estonia
64	69	description	1	Ethiopia
64	70	description	1	Falkland Islands &#40;Islas Malvinas&#41;
64	71	description	1	Faroe Islands
64	72	description	1	Fiji Islands
64	73	description	1	Finland
64	74	description	1	France
64	75	description	1	French Guiana
64	76	description	1	French Polynesia
64	77	description	1	French Southern and Antarctic Lands
64	78	description	1	Gabon
64	79	description	1	Gambia
64	80	description	1	Georgia
64	81	description	1	Germany
64	82	description	1	Ghana
64	83	description	1	Gibraltar
64	84	description	1	Greece
64	85	description	1	Greenland
64	86	description	1	Grenada
64	87	description	1	Guadeloupe
64	88	description	1	Guam
64	89	description	1	Guatemala
64	90	description	1	Guinea
64	91	description	1	Guinea-Bissau
64	92	description	1	Guyana
64	93	description	1	Haiti
64	94	description	1	Heard Island and McDonald Islands
64	95	description	1	Honduras
64	96	description	1	Hong Kong SAR
64	97	description	1	Hungary
64	98	description	1	Iceland
64	99	description	1	India
64	100	description	1	Indonesia
64	101	description	1	Iran
64	102	description	1	Iraq
64	103	description	1	Ireland
64	104	description	1	Israel
64	105	description	1	Italy
64	106	description	1	Jamaica
64	107	description	1	Japan
64	108	description	1	Jordan
64	109	description	1	Kazakhstan
64	110	description	1	Kenya
64	111	description	1	Kiribati
64	112	description	1	Korea
64	113	description	1	Kuwait
64	114	description	1	Kyrgyzstan
64	115	description	1	Laos
64	116	description	1	Latvia
64	117	description	1	Lebanon
64	118	description	1	Lesotho
64	119	description	1	Liberia
64	120	description	1	Libya
64	121	description	1	Liechtenstein
64	122	description	1	Lithuania
64	123	description	1	Luxembourg
64	124	description	1	Macao SAR
64	125	description	1	Macedonia, Former Yugoslav Republic of
64	126	description	1	Madagascar
64	127	description	1	Malawi
64	128	description	1	Malaysia
64	129	description	1	Maldives
64	130	description	1	Mali
64	131	description	1	Malta
64	132	description	1	Marshall Islands
64	133	description	1	Martinique
64	134	description	1	Mauritania
64	135	description	1	Mauritius
64	136	description	1	Mayotte
64	137	description	1	Mexico
64	138	description	1	Micronesia
64	139	description	1	Moldova
64	140	description	1	Monaco
64	141	description	1	Mongolia
64	142	description	1	Montserrat
64	143	description	1	Morocco
64	144	description	1	Mozambique
64	145	description	1	Myanmar
64	146	description	1	Namibia
64	147	description	1	Nauru
64	148	description	1	Nepal
64	149	description	1	Netherlands
64	150	description	1	Netherlands Antilles
64	151	description	1	New Caledonia
64	152	description	1	New Zealand
64	153	description	1	Nicaragua
64	154	description	1	Niger
64	155	description	1	Nigeria
64	156	description	1	Niue
64	157	description	1	Norfolk Island
64	158	description	1	North Korea
64	159	description	1	Northern Mariana Islands
64	160	description	1	Norway
64	161	description	1	Oman
64	162	description	1	Pakistan
64	163	description	1	Palau
64	164	description	1	Panama
64	165	description	1	Papua New Guinea
64	166	description	1	Paraguay
64	167	description	1	Peru
64	168	description	1	Philippines
64	169	description	1	Pitcairn Islands
64	170	description	1	Poland
64	171	description	1	Portugal
64	172	description	1	Puerto Rico
64	173	description	1	Qatar
64	174	description	1	Reunion
64	175	description	1	Romania
64	176	description	1	Russia
64	177	description	1	Rwanda
64	178	description	1	Samoa
64	179	description	1	San Marino
64	180	description	1	Sï¿½1ï¿½7o Tomï¿½1ï¿½7 and Prï¿½1ï¿½7ncipe
64	181	description	1	Saudi Arabia
64	182	description	1	Senegal
64	183	description	1	Serbia and Montenegro
64	184	description	1	Seychelles
64	185	description	1	Sierra Leone
64	186	description	1	Singapore
64	187	description	1	Slovakia
64	188	description	1	Slovenia
64	189	description	1	Solomon Islands
64	190	description	1	Somalia
64	191	description	1	South Africa
64	192	description	1	South Georgia and the South Sandwich Islands
64	193	description	1	Spain
64	194	description	1	Sri Lanka
64	195	description	1	St. Helena
64	196	description	1	St. Kitts and Nevis
64	197	description	1	St. Lucia
64	198	description	1	St. Pierre and Miquelon
64	199	description	1	St. Vincent and the Grenadines
64	200	description	1	Sudan
64	201	description	1	Suriname
64	202	description	1	Svalbard and Jan Mayen
64	203	description	1	Swaziland
64	204	description	1	Sweden
64	205	description	1	Switzerland
64	206	description	1	Syria
64	207	description	1	Taiwan
64	208	description	1	Tajikistan
64	209	description	1	Tanzania
64	210	description	1	Thailand
64	211	description	1	Togo
64	212	description	1	Tokelau
64	213	description	1	Tonga
64	214	description	1	Trinidad and Tobago
64	215	description	1	Tunisia
64	216	description	1	Turkey
64	217	description	1	Turkmenistan
64	218	description	1	Turks and Caicos Islands
64	219	description	1	Tuvalu
64	220	description	1	Uganda
64	221	description	1	Ukraine
64	222	description	1	United Arab Emirates
64	223	description	1	United Kingdom
64	224	description	1	United States
64	225	description	1	United States Minor Outlying Islands
64	226	description	1	Uruguay
64	227	description	1	Uzbekistan
64	228	description	1	Vanuatu
64	229	description	1	Vatican City
64	230	description	1	Venezuela
64	231	description	1	Viet Nam
64	232	description	1	Virgin Islands &#40;British&#41;
64	233	description	1	Virgin Islands
64	234	description	1	Wallis and Futuna
64	235	description	1	Yemen
64	236	description	1	Zambia
64	237	description	1	Zimbabwe
69	1	welcome_message	1	<div> <br/> <p style='font-size:19px; font-weight: bold;'>Welcome to Prancing Pony Billing!</p> <br/> <p style='font-size:14px; text-align=left; padding-left: 15;'>From here, you can review your latest invoice and get it paid instantly. You can also view all your previous invoices and payments, and set up the system for automatic payment with your credit card.</p> <p style='font-size:14px; text-align=left; padding-left: 15;'>What would you like to do today? </p> <ul style='font-size:13px; text-align=left; padding-left: 25;'> <li >To submit a credit card payment, follow the link on the left bar.</li> <li >To view a list of your invoices, click on the âInvoicesâ menu option. The first invoice on the list is your latest invoice. Click on it to see its details.</li> <li>To view a list of your payments, click on the âPaymentsâ menu option. The first payment on the list is your latest payment. Click on it to see its details.</li> <li>To provide a credit card to enable automatic payment, click on the menu option 'Account', and then on 'Edit Credit Card'.</li> </ul> </div>
69	2	welcome_message	1	<div> <br/> <p style='font-size:19px; font-weight: bold;'>Welcome to Mordor Inc. Billing!</p> <br/> <p style='font-size:14px; text-align=left; padding-left: 15;'>From here, you can review your latest invoice and get it paid instantly. You can also view all your previous invoices and payments, and set up the system for automatic payment with your credit card.</p> <p style='font-size:14px; text-align=left; padding-left: 15;'>What would you like to do today? </p> <ul style='font-size:13px; text-align=left; padding-left: 25;'> <li >To submit a credit card payment, follow the link on the left bar.</li> <li >To view a list of your invoices, click on the âInvoicesâ menu option. The first invoice on the list is your latest invoice. Click on it to see its details.</li> <li>To view a list of your payments, click on the âPaymentsâ menu option. The first payment on the list is your latest payment. Click on it to see its details.</li> <li>To provide a credit card to enable automatic payment, click on the menu option 'Account', and then on 'Edit Credit Card'.</li> </ul> </div>
73	1	description	1	Order
73	2	description	1	Invoice
73	3	description	1	Payment
73	4	description	1	Refund
73	5	description	1	Customer
73	6	description	1	Partner
73	7	description	1	Partner selected
73	8	description	1	User
73	9	description	1	Item
81	1	description	1	Active
81	2	description	1	Pending Unsubscription
81	3	description	1	Unsubscribed
81	4	description	1	Pending Expiration
81	5	description	1	Expired
81	6	description	1	Nonsubscriber
81	7	description	1	Discontinued
88	1	description	1	Active
88	2	description	1	Inactive
88	3	description	1	Pending Active
88	4	description	1	Pending Inactive
88	5	description	1	Failed
88	6	description	1	Unavailable
89	1	description	1	None
89	2	description	1	Pre-paid balance
89	3	description	1	Credit limit
90	1	description	1	Paid
90	2	description	1	Unpaid
90	3	description	1	Carried
91	1	description	1	Done and billable
91	2	description	1	Done and not billable
91	3	description	1	Error detected
91	4	description	1	Error declared
14	1	description	2	French Lemonade
14	2600	description	1	Lemonade - Generic
14	2601	description	1	Lemonade - Included in plan
14	2602	description	1	Lemonade 
14	2702	description	1	Long Distance Plan - 1000 min included
14	2701	description	1	Long Distance Plan B - fixed rate
14	2700	description	1	Long Distance Plan A - fixed rate
14	2800	description	1	Long Distance Call
14	2801	description	1	Long Distance Call - Included
14	2900	description	1	Long distance call - Generic
14	3000	description	1	Crazy Brian's Discount Plan
92	1	description	1	Running
92	2	description	1	Finished: successful
92	3	description	1	Finished: failed
23	1	description	1	Item management and order line total calculation
23	2	description	1	Billing process: order filters
23	3	description	1	Billing process: invoice filters
23	4	description	1	Invoice presentation
23	5	description	1	Billing process: order periods calculation
23	6	description	1	Payment gateway integration
23	7	description	1	Notifications
23	8	description	1	Payment instrument selection
23	9	description	1	Penalties for overdue invoices
23	10	description	1	Alarms when a payment gateway is down
23	11	description	1	Subscription status manager
23	12	description	1	Parameters for asynchronous payment processing
23	13	description	1	Add one product to order
23	14	description	1	Product pricing
23	15	description	1	Mediation Reader
23	16	description	1	Mediation Processor
23	17	description	1	Generic internal events listener
23	18	description	1	External provisioning processor
23	19	description	1	Purchase validation against pre-paid balance / credit limit
23	20	description	1	Billing process: customer selection
23	21	description	1	Mediation Error Handler
23	22	description	1	Scheduled Plug-ins
23	23	description	1	Rules Generators
24	1	title	1	Default order totals
24	1	description	1	Calculates the order total and the total for each line, considering the item prices, the quantity and if the prices are percentage or not.
24	2	title	1	VAT
24	2	description	1	Adds an additional line to the order with a percentage charge to represent the value added tax.
24	3	title	1	Invoice due date
24	3	description	1	A very simple implementation that sets the due date of the invoice. The due date is calculated by just adding the period of time to the invoice date.
24	4	title	1	Default invoice composition.
24	4	description	1	This task will copy all the lines on the orders and invoices to the new invoice, considering the periods involved for each order, but not the fractions of periods. It will not copy the lines that are taxes. The quantity and total of each line will be multiplied by the amount of periods.
24	5	title	1	Standard Order Filter
24	5	description	1	Decides if an order should be included in an invoice for a given billing process.  This is done by taking the billing process time span, the order period, the active since/until, etc.
24	6	title	1	Standard Invoice Filter
24	6	description	1	Always returns true, meaning that the overdue invoice will be carried over to a new invoice.
24	7	title	1	Default Order Periods
24	7	description	1	Calculates the start and end period to be included in an invoice. This is done by taking the billing process time span, the order period, the active since/until, etc.
24	8	title	1	Authorize.net payment processor
24	8	description	1	Integration with the authorize.net payment gateway.
24	9	title	1	Standard Email Notification
24	9	description	1	Notifies a user by sending an email. It supports text and HTML emails
24	10	title	1	Default payment information
24	10	description	1	Finds the information of a payment method available to a customer, given priority to credit card. In other words, it will return the credit car of a customer or the ACH information in that order.
24	11	title	1	Testing plug-in for partner payouts
24	11	description	1	Plug-in useful only for testing
24	12	title	1	PDF invoice notification
24	12	description	1	Will generate a PDF version of an invoice.
24	14	title	1	No invoice carry over
24	14	description	1	Returns always false, which makes ibilling to never carry over an invoice into another newer invoice.
24	15	title	1	Default interest task
24	15	description	1	Will create a new order with a penalty item. The item is taken as a parameter to the task.
24	16	title	1	Anticipated order filter
24	16	description	1	Extends BasicOrderFilterTask, modifying the dates to make the order applicable a number of months before it would be by using the default filter.
24	17	title	1	Anticipate order periods.
24	17	description	1	Extends BasicOrderPeriodTask, modifying the dates to make the order applicable a number of months before itd be by using the default task.
24	19	title	1	Email & process authorize.net
24	19	description	1	Extends the standard authorize.net payment processor to also send an email to the company after processing the payment.
24	20	title	1	Payment gateway down alarm
24	20	description	1	Sends an email to the billing administrator as an alarm when a payment gateway is down.
24	21	title	1	Test payment processor
24	21	description	1	A test payment processor implementation to be able to test ibillings functions without using a real payment gateway.
24	22	title	1	Router payment processor based on Custom Fields
24	22	description	1	Allows a customer to be assigned a specific payment gateway. It checks a custom contact field to identify the gateway and then delegates the actual payment processing to another plugin.
24	23	title	1	Default subscription status manager
24	23	description	1	It determines how a payment event affects the subscription status of a user, considering its present status and a state machine.
24	24	title	1	ACH Commerce payment processor
24	24	description	1	Integration with the ACH commerce payment gateway.
24	25	title	1	Standard asynchronous parameters
24	25	description	1	A dummy task that does not add any parameters for asynchronous payment processing. This is the default.
24	26	title	1	Router asynchronous parameters
24	26	description	1	This plug-in adds parameters for asynchronous payment processing to have one processing message bean per payment processor. It is used in combination with the router payment processor plug-ins.
24	28	title	1	Standard Item Manager
24	28	description	1	It adds items to an order. If the item is already in the order, it only updates the quantity.
24	29	title	1	Rules Item Manager
24	58	description	1	Saves the credit card information in the payment gateway, rather than the ibilling DB.
24	59	title	1	Rules Item Manager 2
24	29	description	1	This is a rules-based plug-in. It will do what the basic item manager does (actually calling it), but then it will execute external rules as well. These external rules have full control on changing the order that is getting new items.
24	30	title	1	Rules Line Total
24	30	description	1	This is a rules-based plug-in. It calculates the total for an order line (typically this is the price multiplied by the quantity), allowing for the execution of external rules.
24	31	title	1	Rules Pricing
24	31	description	1	This is a rules-based plug-in. It gives a price to an item by executing external rules. You can then add logic externally for pricing. It is also integrated with the mediation process by having access to the mediation pricing data.
24	32	title	1	Separator file reader
24	32	description	1	This is a reader for the mediation process. It reads records from a text file whose fields are separated by a character (or string).
24	33	title	1	Rules mediation processor
24	33	description	1	This is a rules-based plug-in (see chapter 7). It takes an event record from the mediation process and executes external rules to translate the record into billing meaningful data. This is at the core of the mediation component, see the “Telecom Guide” document for more information.
24	34	title	1	Fixed length file reader
24	34	description	1	This is a reader for the mediation process. It reads records from a text file whose fields have fixed positions,and the record has a fixed length.
24	35	title	1	Payment information without validation
24	35	description	1	This is exactly the same as the standard payment information task, the only difference is that it does not validate if the credit card is expired. Use this plug-in only if you want to submit payment with expired credit cards.
24	36	title	1	Notification task for testing
24	36	description	1	This plug-in is only used for testing purposes. Instead of sending an email (or other real notification), it simply stores the text to be sent in a file named emails_sent.txt.
24	37	title	1	Order periods calculator with pro rating.
24	37	description	1	This plugin takes into consideration the field cycle starts of orders to calculate fractional order periods.
24	38	title	1	Invoice composition task with pro-rating (day as fraction)
24	38	description	1	When creating an invoice from an order, this plug-in will pro-rate any fraction of a period taking a day as the smallest billable unit.
24	39	title	1	Payment process for the Intraanuity payment gateway
24	39	description	1	Integration with the Intraanuity payment gateway.
24	40	title	1	Automatic cancellation credit.
24	40	description	1	This plug-in will create a new order with a negative price to reflect a credit when an order is canceled within a period that has been already invoiced.
24	41	title	1	Fees for early cancellation of a plan.
24	41	description	1	This plug-in will use external rules to determine if an order that is being canceled should create a new order with a penalty fee. This is typically used for early cancels of a contract.
24	42	title	1	Blacklist filter payment processor.
24	42	description	1	Used for blocking payments from reaching real payment processors. Typically configured as first payment processor in the processing chain.
24	43	title	1	Blacklist user when their status becomes suspended or higher.
24	43	description	1	Causes users and their associated details (e.g., credit card number, phone number, etc.) to be blacklisted when their status becomes suspended or higher. 
24	44	title	1	JDBC Mediation Reader.
24	44	description	1	This is a reader for the mediation process. It reads records from a JDBC database source.
24	45	title	1	MySQL Mediation Reader.
24	45	description	1	This is a reader for the mediation process. It is an extension of the JDBC reader, allowing easy configuration of a MySQL database source.
24	46	title	1	Provisioning commands rules task.
24	46	description	1	Responds to order related events. Runs rules to generate commands to send via JMS messages to the external provisioning module.
24	47	title	1	Test external provisioning task.
24	47	description	1	This plug-in is only used for testing purposes. It is a test external provisioning task for testing the provisioning modules.
24	48	title	1	CAI external provisioning task.
24	48	description	1	An external provisioning plug-in for communicating with the Ericsson Customer Administration Interface (CAI).
24	49	title	1	Currency Router payment processor
24	49	description	1	Delegates the actual payment processing to another plug-in based on the currency of the payment.
24	50	title	1	MMSC external provisioning task.
24	50	description	1	An external provisioning plug-in for communicating with the TeliaSonera MMSC.
24	51	title	1	Filters out negative invoices for carry over.
24	51	description	1	This filter will only invoices with a positive balance to be carried over to the next invoice.
24	52	title	1	File invoice exporter.
24	52	description	1	It will generate a file with one line per invoice generated.
24	53	title	1	Rules caller on an event.
24	53	description	1	It will call a package of rules when an internal event happens.
24	54	title	1	Dynamic balance manager
24	54	description	1	It will update the dynamic balance of a customer (pre-paid or credit limit) when events affecting the balance happen.
24	55	title	1	Balance validator based on the customer balance.
24	55	description	1	Used for real-time mediation, this plug-in will validate a call based on the current dynamic balance of a customer.
24	56	title	1	Balance validator based on rules.
24	56	description	1	Used for real-time mediation, this plug-in will validate a call based on a package or rules
24	57	title	1	Payment processor for Payments Gateway.
24	57	description	1	Integration with the Payments Gateway payment processor.
24	58	title	1	Credit cards are stored externally.
50	25	description	1	Use overdue penalties (interest).
50	27	description	1	Use order anticipation.
24	59	description	1	This is a rules-based plug-in compatible with the mediation module of ibilling 2.2.x. It will do what the basic item manager does (actually calling it), but then it will execute external rules as well. These external rules have full control on changing the order that is getting new items.
24	60	title	1	Rules Line Total - 2
24	60	description	1	This is a rules-based plug-in, compatible with the mediation process of ibilling 2.2.x and later. It calculates the total for an order line (typically this is the price multiplied by the quantity), allowing for the execution of external rules.
24	61	title	1	Rules Pricing 2
24	61	description	1	This is a rules-based plug-in compatible with the mediation module of ibilling 2.2.x. It gives a price to an item by executing external rules. You can then add logic externally for pricing. It is also integrated with the mediation process by having access to the mediation pricing data.
24	63	title	1	Test payment processor for external storage.
24	63	description	1	A fake plug-in to test payments that would be stored externally.
24	64	title	1	WorldPay integration
24	64	description	1	Payment processor plug-in to integrate with RBS WorldPay
24	65	title	1	WorldPay integration with external storage
24	65	description	1	Payment processor plug-in to integrate with RBS WorldPay. It stores the credit card information (number, etc) in the gateway.
24	66	title	1	Auto recharge
24	66	description	1	Monitors the balance of a customer and upon reaching a limit, it requests a real-time payment
24	67	title	1	Beanstream gateway integration
24	67	description	1	Payment processor for integration with the Beanstream payment gateway
24	68	title	1	Sage payments gateway integration
24	68	description	1	Payment processor for integration with the Sage payment gateway
24	69	title	1	Standard billing process users filter
24	69	description	1	Called when the billing process runs to select which users to evaluate. This basic implementation simply returns every user not in suspended (or worse) status
24	70	title	1	Selective billing process users filter
24	70	description	1	Called when the billing process runs to select which users to evaluate. This only returns users with orders that have a next invoice date earlier than the billing process.
24	71	title	1	Mediation file error handler
24	71	description	1	Event records with errors are saved to a file
24	73	title	1	Mediation data base error handler
24	73	description	1	Event records with errors are saved to a database table
24	75	title	1	Paypal integration with external storage
24	75	description	1	Submits payments to paypal as a payment gateway and stores credit card information in PayPal as well
24	76	title	1	Authorize.net integration with external storage
24	76	description	1	Submits payments to authorize.net as a payment gateway and stores credit card information in authorize.net as well
24	77	title	1	Payment method router payment processor
24	77	description	1	Delegates the actual payment processing to another plug-in based on the payment method of the payment.
24	78	title	1	Dynamic rules generator
24	78	description	1	Generates rules dynamically based on a Velocity template.
24	79	title	1	Mediation Process Task
24	79	description	1	A scheduled task to execute the Mediation Process.
24	80	title	1	Billing Process Task
24	80	description	1	A scheduled task to execute the Billing Process.
24	90	title	1	Plan Task
24	90	description	1	A task to build plan.
99	1	description	1	Referral Fee
99	2	description	1	Payment Processor
99	3	description	1	IP Address
47	20	description	1	User subscription status has changed
47	32	description	1	User subscription status has NOT changed
47	21	description	1	User account is now locked
47	33	description	1	The dynamic balance of a user has changed
47	34	description	1	The invoice if child flag has changed
47	17	description	1	The order line has been updated
47	18	description	1	The order next billing date has been changed
47	22	description	1	The order main subscription flag was changed
47	26	description	1	An invoiced order was cancelled, a credit order was created
47	24	description	1	A valid payment method was not found. The payment request was cancelled
47	23	description	1	All the one-time orders the mediation found were in status finished
47	27	description	1	A user id was added to the blacklist
47	28	description	1	A user id was removed from the blacklist
47	29	description	1	Posted a provisioning command using a UUID
47	30	description	1	A command was posted for provisioning
47	31	description	1	The provisioning status of an order line has changed
47	25	description	1	A new row has been created
47	19	description	1	Last API call to get the the user subscription status transitions
101	1	description	1	Invoice Reports
100	1	description	1	Total amount invoiced grouped by period.
100	4	description	1	Total payment amount received grouped by period.
101	2	description	1	Order Reports
101	3	description	1	Payment Reports
101	4	description	1	User Reports
100	3	description	1	Number of users subscribed to a specific product.
100	5	description	1	Number of customers created within a period.
100	6	description	1	Total revenue (sum of received payments) per customer.
100	2	description	1	Detailed balance ageing report. Shows the age of outstanding customer balances.
100	7	description	1	Simple accounts receivable report showing current account balances.
100	8	description	1	General ledger details of all invoiced charges for the given day.
14	2314	description	1	磁盘(/GB/天）
100	9	description	1	General ledger summary of all invoiced charges for the given day, grouped by item type.
50	28	description	1	Paypal account.
50	29	description	1	Paypal button URL.
50	30	description	1	URL for HTTP ageing callback.
50	31	description	1	Use continuous invoice dates.
50	32	description	1	Attach PDF invoice to email notification.
50	33	description	1	Force one order per invoice.
50	35	description	1	Add order Id to invoice lines.
50	36	description	1	Allow customers to edit own contact information.
50	37	description	1	Hide (mask) credit card numbers.
50	38	description	1	Link ageing to customer subscriber status.
50	39	description	1	Lock-out user after failed login attempts.
50	40	description	1	Expire user passwords after days.
50	41	description	1	Use main-subscription orders.
50	42	description	1	Use pro-rating.
50	43	description	1	Use payment blacklist.
50	44	description	1	Allow negative payments.
50	45	description	1	Delay negative invoice payments.
50	46	description	1	Allow invoice without orders.
50	47	description	1	Last read mediation record id.
50	4	instruction	1	Grace period in days before ageing a customer with an overdue invoice.
50	5	instruction	1	Partner default percentage commission rate. See the Partner section of the documentation.
50	6	instruction	1	Partner default flat fee to be paid as commission. See the Partner section of the documentation.
50	7	instruction	1	Set to '1' to enable one-time payment for partners. If set, partners will only get paid once per customer. See the Partner section of the documentation.
50	8	instruction	1	Partner default payout period unit. See the Partner section of the documentation.
50	9	instruction	1	Partner default payout period value. See the Partner section of the documentation.
50	10	instruction	1	Set to '1' to enable batch payment payouts using the billing process and the configured payment processor. See the Partner section of the documentation.
50	11	instruction	1	Partner default assigned clerk id. See the Partner section of the documentation.
50	12	instruction	1	Currency ID to use when paying partners. See the Partner section of the documentation.
50	13	instruction	1	Set to '1' to e-mail invoices as the billing company. '0' to deliver invoices as ibilling.
50	14	instruction	1	Set to '1' to show notes in invoices, '0' to disable.
50	15	instruction	1	Days before the orders 'active until' date to send the 1st notification. Leave blank to disable.
50	16	instruction	1	Days before the orders 'active until' date to send the 2nd notification. Leave blank to disable.
50	17	instruction	1	Days before the orders 'active until' date to send the 3rd notification. Leave blank to disable.
50	18	instruction	1	Prefix value for generated invoice public numbers.
50	19	instruction	1	The current value for generated invoice public numbers. New invoices will be assigned a public number by incrementing this value.
50	20	instruction	1	Set to '1' to allow invoices to be deleted, '0' to disable.
50	21	instruction	1	Set to '1' to allow invoice reminder notifications, '0' to disable.
50	24	instruction	1	Set to '1' to enable, '0' to disable.
50	25	instruction	1	Set to '1' to enable the billing process to calculate interest on overdue payments, '0' to disable. Calculation of interest is handled by the selected penalty plug-in.
50	27	instruction	1	Set to '1' to use the "OrderFilterAnticipateTask" to invoice a number of months in advance, '0' to disable. Plug-in must be configured separately.
50	28	instruction	1	PayPal account name.
50	29	instruction	1	A URL where the graphic of the PayPal button resides. The button is displayed to customers when they are making a payment. The default is usually the best option, except when another language is needed.
50	30	instruction	1	URL for the HTTP Callback to invoke when the ageing process changes a status of a user.
50	31	instruction	1	Default "2000-01-01". If this preference is used, the system will make sure that all your invoices have their dates in a incremental way. Any invoice with a greater 'ID' will also have a greater (or equal) date. In other words, a new invoice can not have an earlier date than an existing (older) invoice. To use this preference, set it as a string with the date where to start.
50	32	instruction	1	Set to '1' to attach a PDF version of the invoice to all invoice notification e-mails. '0' to disable.
50	33	instruction	1	Set to '1' to show the "include in separate invoice" flag on an order. '0' to disable.
50	35	instruction	1	Set to '1' to include the ID of the order in the description text of the resulting invoice line. '0' to disable. This can help to easily track which exact orders is responsible for a line in an invoice, considering that many orders can be included in a single invoice.
50	36	instruction	1	Set to '1' to allow customers to edit their own contact information. '0' to disable.
50	37	instruction	1	Set to '1' to mask all credit card numbers. '0' to disable. When set, numbers are masked to all users, even administrators, and in all log files.
50	38	instruction	1	Set to '1' to change the subscription status of a user when the user ages. '0' to disable.
50	39	instruction	1	The number of retries to allow before locking the user account. A locked user account will have their password changed to the value of lockout_password in the ibilling.properties configuration file.
50	40	instruction	1	If greater than zero, it represents the number of days that a password is valid. After those days, the password is expired and the user is forced to change it.
50	41	instruction	1	Set to '1' to allow the usage of the 'main subscription' flag for orders This flag is read only by the mediation process when determining where to place charges coming from external events.
50	42	instruction	1	Set to '1' to allow the use of pro-rating to invoice fractions of a period. Shows the 'cycle' attribute of an order. Note that you need to configure the corresponding plug-ins for this feature to be fully functional.
14	2315	description	1	CentOS 5.6 64位英文版 
50	43	instruction	1	If the payment blacklist feature is used, this is set to the id of the configuration of the PaymentFilterTask plug-in. See the Blacklist section of the documentation.
50	44	instruction	1	Set to '1' to allow negative payments. '0' to disable
50	45	instruction	1	Set to '1' to delay payment of negative invoice amounts, causing the balance to be carried over to the next invoice. Invoices that have had negative balances from other invoices transferred to them are allowed to immediately make a negative payment (credit) if needed. '0' to disable. Preference 44 & 46 are usually also enabled.
50	46	instruction	1	Set to '1' to allow invoices with negative balances to generate a new invoice that isn't composed of any orders so that their balances will always get carried over to a new invoice for the credit to take place. '0' to disable. Preference 44 & 45 are usually also enabled.
50	47	instruction	1	ID of the last record read by the mediation process. This is used to determine what records are "new" and need to be read.
50	48	instruction	1	Set to '1' to allow the use of provisioning. '0' to disable.
50	49	instruction	1	The threshold value for automatic payments. Pre-paid users with an automatic recharge value set will generate an automatic payment whenever the account balance falls below this threshold. Note that you need to configure the AutoRechargeTask plug-in for this feature to be fully functional.
50	48	description	1	Use provisioning.
50	49	description	1	Automatic customer recharge threshold.
104	1	description	1	Invoices
104	2	description	1	Orders
104	3	description	1	Payments
104	4	description	1	Users
23	24	description	1	Ageing for customers with overdue invoices
24	87	title	1	Basic ageing
24	87	description	1	Ages a user based on the number of days that the account is overdue.
24	88	title	1	Ageing process task
24	88	description	1	A scheduled task to execute the Ageing Process.
24	89	title	1	Business day ageing
24	89	description	1	Ages a user based on the number of business days (excluding holidays) that the account is overdue.
59	10	description	1	Create customer
59	11	description	1	Edit customer
59	12	description	1	Delete customer
59	13	description	1	Inspect customer
59	14	description	1	Blacklist customer
59	20	description	1	Create order
59	21	description	1	Edit order
59	22	description	1	Delete order
59	23	description	1	Generate invoice for order
59	30	description	1	Create payment
59	31	description	1	Edit payment
59	32	description	1	Delete payment
59	33	description	1	Link payment to invoice
59	40	description	1	Create product
59	41	description	1	Edit product
59	42	description	1	Delete product
59	50	description	1	Create product category
59	51	description	1	Edit product category
59	52	description	1	Delete product category
59	60	description	1	Create plan
59	61	description	1	Edit plan
59	62	description	1	Delete plan
59	70	description	1	Delete invoice
59	71	description	1	Send invoice notification
59	80	description	1	Approve / Disapprove review
59	90	description	1	Show customer menu
59	91	description	1	Show invoices menu
59	92	description	1	Show order menu
59	93	description	1	Show payments &amp; refunds menu
59	94	description	1	Show billing menu
59	95	description	1	Show mediation menu
59	96	description	1	Show reports menu
59	97	description	1	Show products menu
59	98	description	1	Show plans menu
59	99	description	1	Show configuration menu
59	120	description	1	Web Service API access
59	15	description	1	View customer details
59	16	description	1	Download customer CSV
59	24	description	1	View order details
59	25	description	1	Download order CSV
59	34	description	1	View payment details
59	35	description	1	Download payment CSV
59	43	description	1	View product details
59	44	description	1	Download product CSV
59	63	description	1	View plan details
59	72	description	1	View invoice details
59	73	description	1	Download invoice CSV
59	26	description	1	Edit line price
59	27	description	1	Edit line description
59	28	description	1	View all customers
59	36	description	1	View all customers
59	74	description	1	View all customers
50	50	description	1	Invoice decimal rounding.
50	50	instruction	1	The number of decimal places to be shown on the invoice. Defaults to 2.
60	6	description	1	A customer that will query his/her account
60	6	title	1	Customer
60	7	description	1	The super user of an entity
60	7	title	1	Super user
60	8	description	1	A billing clerk
60	8	title	1	Clerk
14	2302	description	1	CentOS 5.4 x86
14	2303	description	1	CentOS 5.4 32位英文版
14	2304	description	1	Windows Server 2003 SP2 32位英文版
14	2305	description	1	Windows Server 2008 SP2 32位中文标准版
14	2306	description	1	上海电信
14	2307	description	1	上海联通
14	2308	description	1	上海双线
14	2309	description	1	上海双线
14	2310	description	1	四核
14	2311	description	1	双核
14	2312	description	1	IP-单线
14	2313	description	1	IP-双线
14	2316	description	1	Windows Server 2003 R2 32位中文企业版
14	2317	description	1	单核
14	2318	description	1	八核
14	2319	description	1	八核
14	2320	description	1	1G
14	2321	description	1	2G
14	2322	description	1	4G
14	2323	description	1	8G
14	2324	description	1	网络流量（GB）
14	2325	description	1	单核-即付即用
14	2326	description	1	双核-即付即用
14	2327	description	1	四核-即付即用
14	2328	description	1	八核-即付即用
14	2329	description	1	1G-即付即用
14	2330	description	1	2G-即付即用
14	2331	description	1	4G-即付即用
14	2332	description	1	8G-即付即用
14	2333	description	1	祥云A 包月
14	2334	description	1	祥云A 包年
14	2400	description	1	祥云A 即付即用
\.


--
-- TOC entry 2663 (class 0 OID 34816)
-- Dependencies: 193
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY invoice (id, create_datetime, billing_process_id, user_id, delegated_invoice_id, due_date, total, payment_attempts, status_id, balance, carried_balance, in_process_payment, is_review, currency_id, deleted, paper_invoice_batch_id, customer_notes, public_number, last_reminder, overdue_step, create_timestamp, optlock) FROM stdin;
\.


--
-- TOC entry 2664 (class 0 OID 34826)
-- Dependencies: 194
-- Data for Name: invoice_delivery_method; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY invoice_delivery_method (id) FROM stdin;
1
2
3
\.


--
-- TOC entry 2665 (class 0 OID 34829)
-- Dependencies: 195
-- Data for Name: invoice_line; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY invoice_line (id, invoice_id, type_id, amount, quantity, price, deleted, item_id, description, source_user_id, is_percentage, optlock) FROM stdin;
\.


--
-- TOC entry 2666 (class 0 OID 34837)
-- Dependencies: 196
-- Data for Name: invoice_line_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY invoice_line_type (id, description, order_position) FROM stdin;
3	due invoice	1
6	item one-time	3
1	item recurring	2
4	interests	4
5	sub account	5
2	tax	6
\.


--
-- TOC entry 2667 (class 0 OID 34840)
-- Dependencies: 197
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY item (id, internal_number, entity_id, percentage, deleted, has_decimals, optlock, gl_code, price_manual) FROM stdin;
2302	A-01	1	\N	0	1	0	2	0
2303	A-01	1	\N	0	0	0	7	0
2304	A-02	1	\N	0	0	0	5	0
2305	A-03	1	\N	0	0	0	4	0
2306	B-01	1	\N	0	0	0	2	0
2307	B-02	1	\N	0	0	0	2	0
2308	B-03	1	\N	0	1	0	2	0
2309	B-03	1	\N	0	0	0	2	0
2310	cpu-03	1	\N	0	0	1	3	0
2311	cpu-02	1	\N	0	0	1	3	0
2312	A-01	1	\N	0	0	0	2	0
2313	A-02	1	\N	0	0	0	2	0
2314	A-01	1	\N	0	0	0	2	0
2315	A-04	1	\N	0	0	0	3	0
2316	A-05	1	\N	0	0	0	2	0
2317	cpu-01	1	\N	0	0	0	2	0
2318	cpu-05	1	\N	0	1	0	2	0
2319	cpu-04	1	\N	0	0	0	2	0
2320	M-01	1	\N	0	0	0	2	0
2321	M-02	1	\N	0	0	0	2	0
2322	M-03	1	\N	0	0	0	2	0
2323	M-04	1	\N	0	0	0	3	0
2324	net-01	1	\N	0	0	0	2	0
2325	cpu-05	1	\N	0	0	0	3	0
2326	cpu-06	1	\N	0	0	0	3	0
2327	cpu-07	1	\N	0	0	0	3	0
2328	cpu-08	1	\N	0	0	0	3	0
2329	M-05	1	\N	0	0	0	3	0
2330	M-06	1	\N	0	0	0	3	0
2331	M-07	1	\N	0	0	0	3	0
2332	M-08	1	\N	0	0	0	3	0
2333	P-01	1	\N	0	0	0	2	0
2334	P-02	1	\N	0	0	1	3	0
2400	P-03	1	\N	0	0	0	2	0
\.


--
-- TOC entry 2668 (class 0 OID 34845)
-- Dependencies: 198
-- Data for Name: item_price; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY item_price (id, item_id, currency_id, price, optlock) FROM stdin;
2302	2303	1	0.0000000000	0
2303	2304	1	0.0000000000	0
2304	2305	1	0.0000000000	0
2305	2306	1	0.0000000000	0
2306	2307	1	0.0000000000	0
2307	2309	1	0.0000000000	0
2308	2310	1	0.0000000000	0
2309	2311	1	0.0000000000	0
2310	2312	1	30.0000000000	0
2311	2313	1	40.0000000000	0
2312	2314	1	0.0300000000	0
2313	2315	1	0.0000000000	0
2314	2316	1	0.0000000000	0
2315	2317	1	0.0000000000	0
2316	2319	1	0.0000000000	0
2317	2320	1	0.0000000000	0
2318	2321	1	0.0000000000	0
2319	2322	1	0.0000000000	0
2320	2323	1	0.0000000000	0
2321	2324	1	0.6800000000	0
2322	2325	1	0.0700000000	0
2323	2326	1	0.1400000000	0
2324	2327	1	0.2800000000	0
2325	2328	1	0.5600000000	0
2326	2329	1	0.4000000000	0
2327	2330	1	0.8000000000	0
2328	2331	1	1.6000000000	0
2329	2332	1	3.2000000000	0
2330	2333	1	169.0000000000	0
2331	2334	1	1690.0000000000	0
2400	2400	1	0.0000000000	0
\.


--
-- TOC entry 2669 (class 0 OID 34848)
-- Dependencies: 199
-- Data for Name: item_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY item_type (id, entity_id, description, order_line_type_id, optlock) FROM stdin;
2300	1	镜像产品	1	2
2302	1	硬件配置	1	3
2303	1	IP地址	1	2
2304	1	扩展磁盘	1	2
2305	1	套餐	1	2
2310	1	内存	1	2
2312	1	处理器	1	2
2301	1	数据中心	1	2
\.


--
-- TOC entry 2670 (class 0 OID 34851)
-- Dependencies: 200
-- Data for Name: item_type_exclude_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY item_type_exclude_map (item_id, type_id) FROM stdin;
\.


--
-- TOC entry 2671 (class 0 OID 34854)
-- Dependencies: 201
-- Data for Name: item_type_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY item_type_map (item_id, type_id) FROM stdin;
2302	2300
2303	2300
2304	2300
2305	2300
2306	2301
2307	2301
2308	2301
2309	2301
2310	2302
2311	2302
2312	2303
2313	2303
2314	2304
2315	2300
2316	2300
2317	2312
2317	2302
2311	2312
2310	2312
2318	2302
2318	2312
2319	2312
2319	2302
2320	2302
2320	2310
2321	2302
2321	2310
2322	2302
2322	2310
2323	2310
2323	2302
2324	2302
2325	2302
2326	2302
2327	2302
2328	2302
2329	2302
2330	2302
2331	2302
2332	2302
2332	2310
2331	2310
2330	2310
2329	2310
2328	2312
2327	2312
2326	2312
2325	2312
2333	2305
2334	2305
2400	2305
\.

-- TOC entry 2082 (class 0 OID 21247)
-- Dependencies: 238
-- Data for Name: ibilling_constant; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY ibilling_constant (id, name, content) FROM stdin;
1	cpu.generic	2317
2	memory.generic	2320
3	os.win2003.32	2316
4	rule.plan.type	url
5	rule.plan.location	http://localhost:8000/drools-guvnor/org.drools.guvnor.Guvnor/package/ItemManagement/LATEST
6	order.merge	false
\.

--
-- TOC entry 2672 (class 0 OID 34857)
-- Dependencies: 202
-- Data for Name: ibilling_seqs; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY ibilling_seqs (name, next_id) FROM stdin;
permission_type	1
period_unit	1
invoice_delivery_method	1
order_line_type	1
order_billing_type	1
pluggable_task_type_category	1
pluggable_task_type	1
entity_delivery_method_map	4
user_role_map	13
entity_payment_method_map	26
currency_entity_map	10
user_credit_card_map	5
permission_role_map	279
user_status	9
order_status	5
subscriber_status	7
order_line_provisioning_status	1
invoice_line_type	1
currency	2
report_type	1
payment_method	1
payment_result	1
event_log_module	1
event_log_message	1
preference_type	1
notification_message_type	1
country	3
permission	2
currency_exchange	3
billing_process_configuration	1
order_period	1
report	1
partner_range	2
partner	2
entity	1
contact_type	1
payment_info_cheque	17
billing_process	2
process_run	1
process_run_total	1
preference	5
notification_message	1
notification_message_section	1
notification_message_line	1
ageing_entity_step	1
item_type	24
item	31
item_price	2000
purchase_order	1079
order_line	2081
invoice	86
invoice_line	87
order_process	86
payment	19
credit_card	1015
language	1
payment_invoice	1
mediation_cfg	4
mediation_process	1
blacklist	1
generic_status	1
promotion	1
ach	1
partner_payout	1
process_run_total_pm	1
payment_authorization	1
paper_invoice_batch	1
notification_message_arch	1
notification_message_arch_line	1
mediation_record_line	1
balance_type	1
mediation_record	1
filter	1
filter_set	1
shortcut	1
report_parameter	1
pluggable_task	607
contact_field	2027
pluggable_task_parameter	8314
permission_user	10
permission_user	1
permission_user	1
permission_user	1
role	1
event_log	471
base_user	1081
customer	1072
contact_map	7912
contact	1133
recent_item	17
breadcrumb	95
ibilling_constant	7
\.


--
-- TOC entry 2673 (class 0 OID 34860)
-- Dependencies: 203
-- Data for Name: ibilling_table; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY ibilling_table (id, name) FROM stdin;
4	currency
5	entity
6	period_unit
7	invoice_delivery_method
8	entity_delivery_method_map
9	user_status
13	item_type
17	order_period
18	order_line_type
19	order_billing_type
20	order_status
23	pluggable_task_type_category
24	pluggable_task_type
28	contact_type
30	invoice_line_type
31	paper_invoice_batch
32	billing_process
33	process_run
34	billing_process_configuration
35	payment_method
36	entity_payment_method_map
37	process_run_total
38	process_run_total_pm
41	payment_result
45	user_credit_card_map
46	event_log_module
47	event_log_message
50	preference_type
52	notification_message_type
53	notification_message
54	notification_message_section
55	notification_message_line
56	notification_message_arch
57	notification_message_arch_line
58	permission_type
59	permission
60	role
61	permission_role_map
62	user_role_map
64	country
65	promotion
66	payment_authorization
67	currency_exchange
68	currency_entity_map
69	ageing_entity_step
70	partner_payout
75	ach
80	payment_invoice
81	subscriber_status
82	mediation_cfg
83	mediation_process
84	mediation_record
85	blacklist
86	mediation_record_line
88	order_line_provisioning_status
51	preference
11	partner
44	credit_card
42	payment
43	payment_info_cheque
79	partner_range
89	balance_type
48	event_log
39	invoice
90	invoice_status
76	contact_field
40	invoice_line
49	order_process
25	pluggable_task
26	pluggable_task_parameter
3	language
10	base_user
12	customer
29	contact_map
27	contact
14	item
21	purchase_order
22	order_line
87	generic_status
91	mediation_record_status
92	process_run_status
99	contact_field_type
100	report
101	report_type
102	report_parameter
104	notification_category
106	ibilling_constant
\.


--
-- TOC entry 2674 (class 0 OID 34863)
-- Dependencies: 204
-- Data for Name: language; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY language (id, code,description, country_code) FROM stdin;
1	en	English	US
2	zh	Chinese	CN
\.


--
-- TOC entry 2675 (class 0 OID 34866)
-- Dependencies: 205
-- Data for Name: mediation_cfg; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_cfg (id, entity_id, create_datetime, name, order_value, pluggable_task_id, optlock) FROM stdin;
10	1	2007-11-26 10:41:31.55	Asterisk test	1	421	0
20	1	2009-01-13 11:16:50.976	JDBCReader test	2	480	0
30	1	2007-11-26 10:41:31.55	JDBCReader from ibilling	1	6020	1
\.


--
-- TOC entry 2676 (class 0 OID 34869)
-- Dependencies: 206
-- Data for Name: mediation_errors; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_errors (accountcode, src, dst, dcontext, clid, channel, dstchannel, lastapp, lastdata, start, answer, "end", duration, billsec, disposition, amaflags, userfield, error_message, should_retry) FROM stdin;
\.


--
-- TOC entry 2677 (class 0 OID 34875)
-- Dependencies: 207
-- Data for Name: mediation_order_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_order_map (mediation_process_id, order_id) FROM stdin;
\.


--
-- TOC entry 2678 (class 0 OID 34878)
-- Dependencies: 208
-- Data for Name: mediation_process; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_process (id, configuration_id, start_datetime, end_datetime, orders_affected, optlock) FROM stdin;
1	10	2010-06-04 20:50:43.259058	2010-06-04 20:50:43.259058	0	0
\.


--
-- TOC entry 2679 (class 0 OID 34881)
-- Dependencies: 209
-- Data for Name: mediation_record; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_record (id_key, start_datetime, mediation_process_id, optlock, status_id, id) FROM stdin;
20120	2010-06-04 20:50:46.73041	1	0	32	1
\.


--
-- TOC entry 2680 (class 0 OID 34884)
-- Dependencies: 210
-- Data for Name: mediation_record_line; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY mediation_record_line (id, order_line_id, event_date, amount, quantity, description, optlock, mediation_record_id) FROM stdin;
\.


--
-- TOC entry 2681 (class 0 OID 34887)
-- Dependencies: 211
-- Data for Name: notification_category; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_category (id) FROM stdin;
1
2
3
4
\.


--
-- TOC entry 2682 (class 0 OID 34890)
-- Dependencies: 212
-- Data for Name: notification_message; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message (id, type_id, entity_id, language_id, use_flag, optlock) FROM stdin;
1	1	1	1	1	1
2	2	1	1	1	1
3	3	1	1	1	1
7	18	1	1	1	1
8	19	1	1	1	1
17	16	1	1	1	1
18	17	1	1	1	1
19	12	1	1	1	1
\.


--
-- TOC entry 2683 (class 0 OID 34894)
-- Dependencies: 213
-- Data for Name: notification_message_arch; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message_arch (id, type_id, create_datetime, user_id, result_message, optlock) FROM stdin;
\.


--
-- TOC entry 2684 (class 0 OID 34897)
-- Dependencies: 214
-- Data for Name: notification_message_arch_line; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message_arch_line (id, message_archive_id, section, content, optlock) FROM stdin;
\.


--
-- TOC entry 2685 (class 0 OID 34903)
-- Dependencies: 215
-- Data for Name: notification_message_line; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message_line (id, message_section_id, content, optlock) FROM stdin;
1	1	Billing Statement from |company_name|	1
2	2	Some text	1
3	3	Some text	1
4	4	Some text	1
5	5	Some text	1
6	6	Some text	1
\.


--
-- TOC entry 2686 (class 0 OID 34909)
-- Dependencies: 216
-- Data for Name: notification_message_section; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message_section (id, message_id, section, optlock) FROM stdin;
1	1	1	1
2	1	2	1
3	2	1	1
4	2	2	1
5	3	1	1
6	3	2	1
13	7	1	1
14	7	2	1
15	8	1	1
16	8	2	1
\.


--
-- TOC entry 2687 (class 0 OID 34912)
-- Dependencies: 217
-- Data for Name: notification_message_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY notification_message_type (id, optlock, category_id) FROM stdin;
1	1	1
2	1	4
3	1	4
4	1	4
5	1	4
6	1	4
7	1	4
8	1	4
9	1	4
10	1	3
11	1	3
12	1	1
13	1	2
14	1	2
15	1	2
16	1	3
17	1	3
18	1	1
19	1	4
20	1	4
\.


--
-- TOC entry 2688 (class 0 OID 34915)
-- Dependencies: 218
-- Data for Name: order_billing_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY order_billing_type (id) FROM stdin;
1
2
9
\.


--
-- TOC entry 2689 (class 0 OID 34918)
-- Dependencies: 219
-- Data for Name: order_line; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY order_line (id, order_id, item_id, type_id, amount, quantity, price, item_price, create_datetime, deleted, description, provisioning_status, provisioning_request_id, optlock, use_item) FROM stdin;
\.


--
-- TOC entry 2690 (class 0 OID 34925)
-- Dependencies: 220
-- Data for Name: order_line_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY order_line_type (id, editable) FROM stdin;
1	1
2	0
3	0
\.


--
-- TOC entry 2691 (class 0 OID 34928)
-- Dependencies: 221
-- Data for Name: order_period; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY order_period (id, entity_id, value, unit_id, optlock) FROM stdin;
1	\N	\N	\N	1
2	1	1	1	1
4	1	3	1	1
\.


--
-- TOC entry 2692 (class 0 OID 34931)
-- Dependencies: 222
-- Data for Name: order_process; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY order_process (id, order_id, invoice_id, billing_process_id, periods_included, period_start, period_end, is_review, origin, optlock) FROM stdin;
\.


--
-- TOC entry 2693 (class 0 OID 34934)
-- Dependencies: 223
-- Data for Name: paper_invoice_batch; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY paper_invoice_batch (id, total_invoices, delivery_date, is_self_managed, optlock) FROM stdin;
\.


--
-- TOC entry 2694 (class 0 OID 34937)
-- Dependencies: 224
-- Data for Name: partner; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY partner (id, user_id, balance, total_payments, total_refunds, total_payouts, percentage_rate, referral_fee, fee_currency_id, one_time, period_unit_id, period_value, next_payout_date, due_payout, automatic_process, related_clerk, optlock) FROM stdin;
\.


--
-- TOC entry 2695 (class 0 OID 34940)
-- Dependencies: 225
-- Data for Name: partner_payout; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY partner_payout (id, starting_date, ending_date, payments_amount, refunds_amount, balance_left, payment_id, partner_id, optlock) FROM stdin;
\.


--
-- TOC entry 2696 (class 0 OID 34943)
-- Dependencies: 226
-- Data for Name: partner_range; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY partner_range (id, partner_id, percentage_rate, referral_fee, range_from, range_to, optlock) FROM stdin;
\.


--
-- TOC entry 2697 (class 0 OID 34946)
-- Dependencies: 227
-- Data for Name: payment; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment (id, user_id, attempt, result_id, amount, create_datetime, update_datetime, payment_date, method_id, credit_card_id, deleted, is_refund, is_preauth, payment_id, currency_id, payout_id, ach_id, balance, optlock, payment_period, payment_notes) FROM stdin;
\.


--
-- TOC entry 2698 (class 0 OID 34955)
-- Dependencies: 228
-- Data for Name: payment_authorization; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment_authorization (id, payment_id, processor, code1, code2, code3, approval_code, avs, transaction_id, md5, card_code, create_datetime, response_message, optlock) FROM stdin;
\.


--
-- TOC entry 2699 (class 0 OID 34961)
-- Dependencies: 229
-- Data for Name: payment_info_cheque; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment_info_cheque (id, payment_id, bank, cheque_number, cheque_date, optlock) FROM stdin;
\.


--
-- TOC entry 2700 (class 0 OID 34964)
-- Dependencies: 230
-- Data for Name: payment_invoice; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment_invoice (id, payment_id, invoice_id, amount, create_datetime, optlock) FROM stdin;
\.


--
-- TOC entry 2701 (class 0 OID 34967)
-- Dependencies: 231
-- Data for Name: payment_method; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment_method (id) FROM stdin;
1
2
3
4
5
6
7
8
9
\.


--
-- TOC entry 2702 (class 0 OID 34970)
-- Dependencies: 232
-- Data for Name: payment_result; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY payment_result (id) FROM stdin;
1
2
3
4
\.


--
-- TOC entry 2703 (class 0 OID 34973)
-- Dependencies: 233
-- Data for Name: period_unit; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY period_unit (id) FROM stdin;
1
2
3
4
\.


--
-- TOC entry 2704 (class 0 OID 34976)
-- Dependencies: 234
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY permission (id, type_id, foreign_id) FROM stdin;
10	1	\N
11	1	\N
12	1	\N
13	1	\N
14	1	\N
20	2	\N
21	2	\N
22	2	\N
23	2	\N
30	3	\N
31	3	\N
32	3	\N
33	3	\N
40	4	\N
41	4	\N
42	4	\N
50	5	\N
51	5	\N
52	5	\N
60	6	\N
61	6	\N
62	6	\N
70	7	\N
71	7	\N
80	8	\N
90	9	\N
91	9	\N
92	9	\N
93	9	\N
94	9	\N
95	9	\N
96	9	\N
97	9	\N
98	9	\N
99	9	\N
120	10	\N
15	1	\N
16	1	\N
24	2	\N
25	2	\N
34	3	\N
35	3	\N
43	4	\N
44	4	\N
63	6	\N
72	7	\N
73	7	\N
26	2	\N
27	2	\N
28	2	\N
36	3	\N
74	7	\N
\.


--
-- TOC entry 2705 (class 0 OID 34979)
-- Dependencies: 235
-- Data for Name: permission_role_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY permission_role_map (permission_id, role_id) FROM stdin;
10	2
11	2
12	2
13	2
14	2
20	2
21	2
22	2
23	2
30	2
31	2
32	2
33	2
40	2
41	2
42	2
50	2
51	2
52	2
60	2
61	2
62	2
70	2
71	2
80	2
90	2
91	2
92	2
93	2
94	2
95	2
96	2
97	2
98	2
99	2
120	2
15	2
16	2
24	2
25	2
34	2
35	2
43	2
44	2
63	2
72	2
73	2
26	2
27	2
28	2
36	2
74	2
24	5
30	5
34	5
72	5
91	5
92	5
93	5
28	3
36	3
74	3
\.


--
-- TOC entry 2706 (class 0 OID 34982)
-- Dependencies: 236
-- Data for Name: permission_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY permission_type (id, description) FROM stdin;
1	Customer
2	Order
3	Payment
4	Product
5	Product Category
6	Plan
7	Invoice
8	Billing
9	Menu
10	API
\.


--
-- TOC entry 2707 (class 0 OID 34985)
-- Dependencies: 237
-- Data for Name: permission_user; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY permission_user (permission_id, user_id, is_grant, id) FROM stdin;
\.


--
-- TOC entry 2708 (class 0 OID 34988)
-- Dependencies: 238
-- Data for Name: pluggable_task; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY pluggable_task (id, entity_id, type_id, processing_order, optlock, notes) FROM stdin;
2	1	3	1	1	\N
3	1	38	2	2	\N
4	1	5	1	1	\N
5	1	6	1	1	\N
6	1	37	1	2	\N
7	1	36	1	1	\N
8	1	10	1	1	\N
9	1	12	2	1	\N
19	1	20	1	1	\N
21	1	22	2	1	\N
23	1	23	1	1	\N
31	1	26	1	1	\N
420	1	33	1	1	\N
421	1	32	1	3	\N
431	1	1	2	1	\N
440	1	40	1	1	\N
450	1	41	1	1	\N
460	1	42	1	1	\N
470	1	43	1	1	\N
480	1	44	3	1	\N
490	1	46	1	2	\N
500	1	47	1	1	\N
510	1	48	2	1	\N
520	1	49	3	1	\N
20	1	21	4	1	\N
22	1	21	5	1	\N
540	1	53	1	2	\N
541	1	54	1	1	\N
550	1	55	1	1	\N
560	1	56	2	3	\N
570	1	15	1	1	\N
571	1	71	1	1	\N
410	1	61	1	1	\N
600	1	66	1	1	\N
1	1	59	1	3	\N
430	1	60	1	1	\N
572	1	73	2	0	\N
6020	1	44	2	1	\N
6030	1	81	1	1	\N
6040	1	78	1	4	\N
6050	1	82	2	7	\N
6060	1	87	1	1	\N
6061	1	88	3	1	\N
530	1	50	3	2	\N
\.


--
-- TOC entry 2709 (class 0 OID 34994)
-- Dependencies: 239
-- Data for Name: pluggable_task_parameter; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY pluggable_task_parameter (id, task_id, name, int_value, str_value, float_value, optlock) FROM stdin;
1	9	design	\N	simple_invoice_b2b	\N	1
2	7	smtp_server	\N		\N	1
3	7	from	\N	admin@prancingpony.me	\N	1
4	7	username	\N		\N	1
5	7	password	\N		\N	1
6	7	port	\N		\N	1
7	7	reply_to	\N		\N	1
8	7	bcc_to	\N		\N	1
9	7	from_name	\N	Prancing Pony	\N	1
19	19	failed_limit	10	\N	\N	1
20	19	failed_time	10	\N	\N	1
21	19	time_between_alarms	30	\N	\N	1
22	19	email_address	\N		\N	1
24	20	all	\N	yes	\N	1
35	20	processor_name	\N	first_fake_processor	\N	1
36	21	custom_field_id	\N	2	\N	1
37	21	FAKE_2	\N	22	\N	1
38	22	all	\N	yes	\N	1
39	22	processor_name	\N	second_fake_processor	\N	1
44	21	FAKE_1	\N	20	\N	1
540	421	format_file	\N	asterisk.xml	\N	1
550	421	suffix	\N	csv	\N	1
551	421	batch_size	\N	100	\N	1
560	450	file	\N	CancelFees.pkg	\N	1
610	460	enable_filter_user_id	\N	true	\N	1
620	460	enable_filter_name	\N	true	\N	1
630	460	enable_filter_cc_number	\N	true	\N	1
640	460	enable_filter_address	\N	true	\N	1
650	460	enable_filter_ip_address	\N	true	\N	1
660	460	enable_filter_phone_number	\N	true	\N	1
670	460	ip_address_ccf_id	3	\N	\N	1
680	490	file	\N	provisioning_commands.pkg	\N	1
690	23	item_type_id	\N	1	\N	1
700	510	username	\N	test-username	\N	1
710	510	password	\N	test-password	\N	1
720	510	remove	\N	VOID	\N	1
730	520	USD	\N	20	\N	1
740	520	AUD	\N	22	\N	1
750	21	CURRENCY_ROUTER	\N	520	\N	1
760	530	loginUser	\N	test-username	\N	1
770	530	loginPassword	\N	test-password	\N	1
780	530	portalId	\N	test-portalId	\N	1
790	530	applicationId	\N	test-applicationId	\N	1
800	530	bnet	\N	test-bnet	\N	1
8100	540	file	\N	InternalEventsRulesTask520.pkg	\N	1
8200	560	file	\N	ValidatePurchaseRules.pkg	\N	1
8301	570	item	270	\N	\N	1
8302	570	ageing_step	6	\N	\N	1
590	410	file	\N	PricingRules.pkg RateCard.pkg	\N	2
580	420	file	\N	Mediation.pkg ItemsRules.pkg PricingRules.pkg RateCard.pkg	\N	1
8305	572	url	\N	jdbc:postgresql://localhost:5432/ibilling	\N	1
8306	572	username	\N	ibilling	\N	1
830700	6020	table_name	\N	cdrentries	\N	1
830701	6020	key_column_name	\N	accountcode	\N	1
830702	6020	driver	\N	org.postgresql.Driver	\N	1
830703	6020	url	\N	jdbc:postgresql://localhost:5432/ibilling	\N	1
830704	6020	username	\N	ibilling	\N	1
830705	6020	password	\N		\N	1
830706	6020	timestamp_column_name	\N	ts	\N	1
830707	22	accept-ach	\N	true	\N	0
830800	6040	config_filename	\N	rules-generator-config.xml	\N	1
830801	6040	output_filename	\N	InternalEventsRulesTask520.pkg	\N	1
830802	6040	template_filename	\N	rules-generator-template-integration-test.vm	\N	1
831200	6050	start_time	\N	20100728-0000	\N	1
831300	530	msisdn	\N	1	\N	0
831301	530	subscriptionType	\N	1	\N	0
831302	530	mmsCapability	\N	1	\N	0
831303	530	methodName	\N	test	\N	0
600	1	file	\N	ItemsRules.pkg	\N	1
570	430	file	\N	21viacloud.pkg	\N	1
\.


--
-- TOC entry 2710 (class 0 OID 35000)
-- Dependencies: 240
-- Data for Name: pluggable_task_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY pluggable_task_type (id, category_id, class_name, min_parameters) FROM stdin;
1	1	com.infosense.ibilling.server.pluggableTask.BasicLineTotalTask	0
2	1	com.infosense.ibilling.server.pluggableTask.GSTTaxTask	2
3	4	com.infosense.ibilling.server.pluggableTask.CalculateDueDate	0
4	4	com.infosense.ibilling.server.pluggableTask.BasicCompositionTask	0
5	2	com.infosense.ibilling.server.pluggableTask.BasicOrderFilterTask	0
6	3	com.infosense.ibilling.server.pluggableTask.BasicInvoiceFilterTask	0
7	5	com.infosense.ibilling.server.pluggableTask.BasicOrderPeriodTask	0
8	6	com.infosense.ibilling.server.pluggableTask.PaymentAuthorizeNetTask	2
9	7	com.infosense.ibilling.server.pluggableTask.BasicEmailNotificationTask	6
10	8	com.infosense.ibilling.server.pluggableTask.BasicPaymentInfoTask	0
11	6	com.infosense.ibilling.server.pluggableTask.PaymentPartnerTestTask	0
12	7	com.infosense.ibilling.server.pluggableTask.PaperInvoiceNotificationTask	1
13	4	com.infosense.ibilling.server.pluggableTask.CalculateDueDateDfFm	0
14	3	com.infosense.ibilling.server.pluggableTask.NoInvoiceFilterTask	0
15	17	com.infosense.ibilling.server.pluggableTask.BasicPenaltyTask	2
16	2	com.infosense.ibilling.server.pluggableTask.OrderFilterAnticipatedTask	0
17	5	com.infosense.ibilling.server.pluggableTask.OrderPeriodAnticipateTask	0
18	6	com.infosense.ibilling.server.pluggableTask.PaymentBitMoversTask	0
19	6	com.infosense.ibilling.server.pluggableTask.PaymentEmailAuthorizeNetTask	1
20	10	com.infosense.ibilling.server.pluggableTask.ProcessorEmailAlarmTask	3
21	6	com.infosense.ibilling.server.pluggableTask.PaymentFakeTask	0
23	11	com.infosense.ibilling.server.user.tasks.BasicSubscriptionStatusManagerTask	0
24	6	com.infosense.ibilling.server.user.tasks.PaymentACHCommerceTask	5
25	12	com.infosense.ibilling.server.payment.tasks.NoAsyncParameters	0
26	12	com.infosense.ibilling.server.payment.tasks.RouterAsyncParameters	0
28	13	com.infosense.ibilling.server.item.tasks.BasicItemManager	0
29	13	com.infosense.ibilling.server.item.tasks.RulesItemManager	0
30	1	com.infosense.ibilling.server.order.task.RulesLineTotalTask	0
31	14	com.infosense.ibilling.server.item.tasks.RulesPricingTask	0
32	15	com.infosense.ibilling.server.mediation.task.SeparatorFileReader	1
33	16	com.infosense.ibilling.server.mediation.task.RulesMediationTask	0
34	15	com.infosense.ibilling.server.mediation.task.FixedFileReader	1
35	8	com.infosense.ibilling.server.user.tasks.PaymentInfoNoValidateTask	0
36	7	com.infosense.ibilling.server.notification.task.TestNotificationTask	0
37	5	com.infosense.ibilling.server.process.task.ProRateOrderPeriodTask	0
38	4	com.infosense.ibilling.server.process.task.DailyProRateCompositionTask	0
39	6	com.infosense.ibilling.server.payment.tasks.PaymentAtlasTask	2
40	17	com.infosense.ibilling.server.order.task.RefundOnCancelTask	0
41	17	com.infosense.ibilling.server.order.task.CancellationFeeRulesTask	0
42	6	com.infosense.ibilling.server.payment.tasks.PaymentFilterTask	0
43	17	com.infosense.ibilling.server.payment.blacklist.tasks.BlacklistUserStatusTask	0
44	15	com.infosense.ibilling.server.mediation.task.JDBCReader	0
45	15	com.infosense.ibilling.server.mediation.task.MySQLReader	0
46	17	com.infosense.ibilling.server.provisioning.task.ProvisioningCommandsRulesTask	0
47	18	com.infosense.ibilling.server.provisioning.task.TestExternalProvisioningTask	0
22	6	com.infosense.ibilling.server.payment.tasks.PaymentRouterCCFTask	2
48	18	com.infosense.ibilling.server.provisioning.task.CAIProvisioningTask	2
49	6	com.infosense.ibilling.server.payment.tasks.PaymentRouterCurrencyTask	2
50	18	com.infosense.ibilling.server.provisioning.task.MMSCProvisioningTask	5
51	3	com.infosense.ibilling.server.invoice.task.NegativeBalanceInvoiceFilterTask	0
52	17	com.infosense.ibilling.server.invoice.task.FileInvoiceExportTask	1
53	17	com.infosense.ibilling.server.system.event.task.InternalEventsRulesTask	0
54	17	com.infosense.ibilling.server.user.balance.DynamicBalanceManagerTask	0
55	19	com.infosense.ibilling.server.user.tasks.UserBalanceValidatePurchaseTask	0
56	19	com.infosense.ibilling.server.user.tasks.RulesValidatePurchaseTask	0
57	6	com.infosense.ibilling.server.payment.tasks.PaymentsGatewayTask	4
58	17	com.infosense.ibilling.server.payment.tasks.SaveCreditCardExternallyTask	1
59	13	com.infosense.ibilling.server.order.task.RulesItemManager2	0
60	1	com.infosense.ibilling.server.order.task.RulesLineTotalTask2	0
61	14	com.infosense.ibilling.server.item.tasks.RulesPricingTask2	0
63	6	com.infosense.ibilling.server.pluggableTask.PaymentFakeExternalStorage	0
64	6	com.infosense.ibilling.server.payment.tasks.PaymentWorldPayTask	3
65	6	com.infosense.ibilling.server.payment.tasks.PaymentWorldPayExternalTask	3
66	17	com.infosense.ibilling.server.user.tasks.AutoRechargeTask	0
67	6	com.infosense.ibilling.server.payment.tasks.PaymentBeanstreamTask	3
68	6	com.infosense.ibilling.server.payment.tasks.PaymentSageTask	2
69	20	com.infosense.ibilling.server.process.task.BasicBillingProcessFilterTask	0
70	20	com.infosense.ibilling.server.process.task.BillableUsersBillingProcessFilterTask	0
71	21	com.infosense.ibilling.server.mediation.task.SaveToFileMediationErrorHandler	0
73	21	com.infosense.ibilling.server.mediation.task.SaveToJDBCMediationErrorHandler	1
75	6	com.infosense.ibilling.server.payment.tasks.PaymentPaypalExternalTask	3
76	6	com.infosense.ibilling.server.payment.tasks.PaymentAuthorizeNetCIMTask	2
77	6	com.infosense.ibilling.server.payment.tasks.PaymentMethodRouterTask	4
78	23	com.infosense.ibilling.server.rule.task.VelocityRulesGeneratorTask	2
81	22	com.infosense.ibilling.server.mediation.task.MediationProcessTask	0
82	22	com.infosense.ibilling.server.billing.task.BillingProcessTask	1
84	17	com.infosense.ibilling.server.payment.tasks.SaveACHExternallyTask	1
85	20	com.infosense.ibilling.server.process.task.BillableUserOrdersBillingProcessFilterTask	0
86	4	com.infosense.ibilling.server.process.task.SimpleTaxCompositionTask	1
87	24	com.infosense.ibilling.server.process.task.BasicAgeingTask	0
88	22	com.infosense.ibilling.server.process.task.AgeingProcessTask	0
89	24	com.infosense.ibilling.server.process.task.BusinessDayAgeingTask	0
90	1	com.infosense.ibilling.server.order.task.PlanOrderTask	0
\.


--
-- TOC entry 2711 (class 0 OID 35003)
-- Dependencies: 241
-- Data for Name: pluggable_task_type_category; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY pluggable_task_type_category (id, interface_name) FROM stdin;
1	com.infosense.ibilling.server.pluggableTask.OrderProcessingTask
2	com.infosense.ibilling.server.pluggableTask.OrderFilterTask
3	com.infosense.ibilling.server.pluggableTask.InvoiceFilterTask
4	com.infosense.ibilling.server.pluggableTask.InvoiceCompositionTask
5	com.infosense.ibilling.server.pluggableTask.OrderPeriodTask
6	com.infosense.ibilling.server.pluggableTask.PaymentTask
7	com.infosense.ibilling.server.pluggableTask.NotificationTask
8	com.infosense.ibilling.server.pluggableTask.PaymentInfoTask
9	com.infosense.ibilling.server.pluggableTask.PenaltyTask
10	com.infosense.ibilling.server.pluggableTask.ProcessorAlarm
11	com.infosense.ibilling.server.user.tasks.ISubscriptionStatusManager
12	com.infosense.ibilling.server.payment.tasks.IAsyncPaymentParameters
13	com.infosense.ibilling.server.item.tasks.IItemPurchaseManager
14	com.infosense.ibilling.server.item.tasks.IPricing
15	com.infosense.ibilling.server.mediation.task.IMediationReader
16	com.infosense.ibilling.server.mediation.task.IMediationProcess
17	com.infosense.ibilling.server.system.event.task.IInternalEventsTask
18	com.infosense.ibilling.server.provisioning.task.IExternalProvisioning
19	com.infosense.ibilling.server.user.tasks.IValidatePurchaseTask
20	com.infosense.ibilling.server.process.task.IBillingProcessFilterTask
21	com.infosense.ibilling.server.mediation.task.IMediationErrorHandler
22	com.infosense.ibilling.server.process.task.IScheduledTask
23	com.infosense.ibilling.server.rule.task.IRulesGenerator
24	com.infosense.ibilling.server.process.task.IAgeingTask
\.


--
-- TOC entry 2712 (class 0 OID 35006)
-- Dependencies: 242
-- Data for Name: preference; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY preference (id, type_id, table_id, foreign_id, value) FROM stdin;
43	28	5	1	admin@prancingpony.me
4	4	5	1	5
7	7	5	1	0
8	8	5	1	1
9	9	5	1	1
10	10	5	1	0
11	11	5	1	1
12	12	5	1	1
13	13	5	1	1
14	14	5	1	0
28	4	5	2	5
31	7	5	2	0
32	8	5	2	1
33	9	5	2	1
34	10	5	2	0
35	11	5	2	12
36	12	5	2	1
37	13	5	2	1
38	14	5	2	0
39	39	5	1	3
40	40	5	1	60
41	19	5	2	2
42	41	5	1	1
45	43	5	1	460
46	44	5	1	1
15	19	5	1	1023
5	5	5	1	10.0000000000
6	6	5	1	0.0000000000
29	5	5	2	10.0000000000
30	6	5	2	0.0000000000
47	49	5	1	5.0000000000
\.


--
-- TOC entry 2713 (class 0 OID 35009)
-- Dependencies: 243
-- Data for Name: preference_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY preference_type (id, def_value) FROM stdin;
4	\N
5	\N
6	\N
7	\N
8	\N
9	\N
10	\N
11	\N
12	\N
13	\N
14	\N
15	\N
16	\N
17	\N
18	\N
22	\N
23	\N
28	\N
29	https://www.paypal.com/en_US/i/btn/x-click-but6.gif
30	\N
31	2000-01-01
49	\N
19	1
20	1
21	0
24	0
25	0
27	0
32	0
33	0
35	0
36	1
37	0
38	1
39	0
40	0
41	0
42	1
43	0
44	0
45	0
46	0
47	0
48	1
50	2
\.


--
-- TOC entry 2714 (class 0 OID 35012)
-- Dependencies: 244
-- Data for Name: process_run; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY process_run (id, process_id, run_date, started, finished, payment_finished, invoices_generated, optlock, status_id) FROM stdin;
\.


--
-- TOC entry 2715 (class 0 OID 35015)
-- Dependencies: 245
-- Data for Name: process_run_total; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY process_run_total (id, process_run_id, currency_id, total_invoiced, total_paid, total_not_paid, optlock) FROM stdin;
\.


--
-- TOC entry 2716 (class 0 OID 35018)
-- Dependencies: 246
-- Data for Name: process_run_total_pm; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY process_run_total_pm (id, process_run_total_id, payment_method_id, total, optlock) FROM stdin;
\.


--
-- TOC entry 2717 (class 0 OID 35021)
-- Dependencies: 247
-- Data for Name: process_run_user; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY process_run_user (id, process_run_id, user_id, status, created, optlock) FROM stdin;
\.


--
-- TOC entry 2718 (class 0 OID 35025)
-- Dependencies: 248
-- Data for Name: promotion; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY promotion (id, item_id, code, notes, once, since, until) FROM stdin;
\.


--
-- TOC entry 2719 (class 0 OID 35028)
-- Dependencies: 249
-- Data for Name: promotion_user_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY promotion_user_map (user_id, promotion_id) FROM stdin;
\.


--
-- TOC entry 2720 (class 0 OID 35031)
-- Dependencies: 250
-- Data for Name: purchase_order; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY purchase_order (id, user_id, period_id, billing_type_id, active_since, active_until, cycle_start, create_datetime, next_billable_day, created_by, status_id, currency_id, deleted, excludeFromBp, notify, last_notified, notification_step, due_date_unit_id, due_date_value, df_fm, anticipate_periods, own_invoice, notes, notes_in_invoice, is_current, optlock) FROM stdin;
\.


--
-- TOC entry 2721 (class 0 OID 35035)
-- Dependencies: 251
-- Data for Name: recent_item; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY recent_item (id, type, object_id, user_id, version) FROM stdin;
12	PLUGIN	430	1	0
13	PLUGIN	430	1	0
14	PLUGIN	430	1	0
15	CUSTOMER	10801	1	0
16	CUSTOMER	10801	1	0
\.


--
-- TOC entry 2722 (class 0 OID 35038)
-- Dependencies: 252
-- Data for Name: report; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY report (id, type_id, name, file_name, optlock) FROM stdin;
\.


--
-- TOC entry 2723 (class 0 OID 35044)
-- Dependencies: 253
-- Data for Name: report_parameter; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY report_parameter (id, report_id, dtype, name) FROM stdin;
1	1	date	start_date
2	1	date	end_date
3	1	integer	period
5	4	date	start_date
6	4	date	end_date
7	4	integer	period
4	3	integer	item_id
8	5	date	start_date
9	5	date	end_date
10	5	integer	period
11	6	date	start_date
12	6	date	end_date
13	8	date	date
14	9	date	date
\.


--
-- TOC entry 2724 (class 0 OID 35047)
-- Dependencies: 254
-- Data for Name: report_type; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY report_type (id, name, optlock) FROM stdin;
1	invoice	0
2	order	0
3	payment	0
4	user	0
\.


--
-- TOC entry 2725 (class 0 OID 35050)
-- Dependencies: 255
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY role (id, entity_id, role_type_id) FROM stdin;
2	1	2
3	1	3
5	1	5
\.


--
-- TOC entry 2726 (class 0 OID 35053)
-- Dependencies: 256
-- Data for Name: shortcut; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY shortcut (id, user_id, controller, action, name, object_id, version) FROM stdin;
\.


--
-- TOC entry 2727 (class 0 OID 35059)
-- Dependencies: 257
-- Data for Name: user_credit_card_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY user_credit_card_map (user_id, credit_card_id) FROM stdin;
\.


--
-- TOC entry 2728 (class 0 OID 35062)
-- Dependencies: 258
-- Data for Name: user_role_map; Type: TABLE DATA; Schema: public; Owner: ibilling
--

COPY user_role_map (user_id, role_id) FROM stdin;
1	2
10801	5
\.


--
-- TOC entry 2282 (class 2606 OID 35066)
-- Dependencies: 161 161
-- Name: ach_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY ach
    ADD CONSTRAINT ach_pkey PRIMARY KEY (id);


--
-- TOC entry 2284 (class 2606 OID 35068)
-- Dependencies: 162 162
-- Name: ageing_entity_step_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY ageing_entity_step
    ADD CONSTRAINT ageing_entity_step_pkey PRIMARY KEY (id);


--
-- TOC entry 2286 (class 2606 OID 35070)
-- Dependencies: 163 163
-- Name: base_user_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY base_user
    ADD CONSTRAINT base_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2291 (class 2606 OID 35072)
-- Dependencies: 165 165
-- Name: billing_process_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY billing_process_configuration
    ADD CONSTRAINT billing_process_configuration_pkey PRIMARY KEY (id);


--
-- TOC entry 2289 (class 2606 OID 35074)
-- Dependencies: 164 164
-- Name: billing_process_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY billing_process
    ADD CONSTRAINT billing_process_pkey PRIMARY KEY (id);


--
-- TOC entry 2293 (class 2606 OID 35076)
-- Dependencies: 166 166
-- Name: blacklist_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY blacklist
    ADD CONSTRAINT blacklist_pkey PRIMARY KEY (id);


--
-- TOC entry 2297 (class 2606 OID 35078)
-- Dependencies: 167 167
-- Name: breadcrumb_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY breadcrumb
    ADD CONSTRAINT breadcrumb_pkey PRIMARY KEY (id);


--
-- TOC entry 2299 (class 2606 OID 35080)
-- Dependencies: 168 168
-- Name: cdrentries_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY cdrentries
    ADD CONSTRAINT cdrentries_pkey PRIMARY KEY (id);


--
-- TOC entry 2310 (class 2606 OID 35082)
-- Dependencies: 170 170
-- Name: contact_field_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY contact_field
    ADD CONSTRAINT contact_field_pkey PRIMARY KEY (id);


--
-- TOC entry 2314 (class 2606 OID 35084)
-- Dependencies: 171 171
-- Name: contact_field_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY contact_field_type
    ADD CONSTRAINT contact_field_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2319 (class 2606 OID 35086)
-- Dependencies: 172 172
-- Name: contact_map_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY contact_map
    ADD CONSTRAINT contact_map_pkey PRIMARY KEY (id);


--
-- TOC entry 2302 (class 2606 OID 35088)
-- Dependencies: 169 169
-- Name: contact_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (id);


--
-- TOC entry 2321 (class 2606 OID 35090)
-- Dependencies: 173 173
-- Name: contact_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY contact_type
    ADD CONSTRAINT contact_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2323 (class 2606 OID 35092)
-- Dependencies: 174 174
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);


--
-- TOC entry 2325 (class 2606 OID 35094)
-- Dependencies: 175 175
-- Name: credit_card_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY credit_card
    ADD CONSTRAINT credit_card_pkey PRIMARY KEY (id);


--
-- TOC entry 2332 (class 2606 OID 35096)
-- Dependencies: 178 178
-- Name: currency_exchange_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY currency_exchange
    ADD CONSTRAINT currency_exchange_pkey PRIMARY KEY (id);


--
-- TOC entry 2329 (class 2606 OID 35098)
-- Dependencies: 176 176
-- Name: currency_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (id);


--
-- TOC entry 2335 (class 2606 OID 35100)
-- Dependencies: 179 179
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2337 (class 2606 OID 35102)
-- Dependencies: 180 180
-- Name: entity_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_pkey PRIMARY KEY (id);


--
-- TOC entry 2342 (class 2606 OID 35104)
-- Dependencies: 185 185
-- Name: event_log_message_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY event_log_message
    ADD CONSTRAINT event_log_message_pkey PRIMARY KEY (id);


--
-- TOC entry 2344 (class 2606 OID 35106)
-- Dependencies: 186 186
-- Name: event_log_module_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY event_log_module
    ADD CONSTRAINT event_log_module_pkey PRIMARY KEY (id);


--
-- TOC entry 2339 (class 2606 OID 35108)
-- Dependencies: 184 184
-- Name: event_log_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_pkey PRIMARY KEY (id);


--
-- TOC entry 2346 (class 2606 OID 35110)
-- Dependencies: 187 187
-- Name: filter_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY filter
    ADD CONSTRAINT filter_pkey PRIMARY KEY (id);


--
-- TOC entry 2348 (class 2606 OID 35112)
-- Dependencies: 188 188
-- Name: filter_set_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY filter_set
    ADD CONSTRAINT filter_set_pkey PRIMARY KEY (id);


--
-- TOC entry 2350 (class 2606 OID 35114)
-- Dependencies: 190 190
-- Name: generic_status_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY generic_status
    ADD CONSTRAINT generic_status_pkey PRIMARY KEY (id);


--
-- TOC entry 2352 (class 2606 OID 35116)
-- Dependencies: 191 191
-- Name: generic_status_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY generic_status_type
    ADD CONSTRAINT generic_status_type_pkey PRIMARY KEY (id);

--
-- TOC entry 2081 (class 2606 OID 21253)
-- Dependencies: 238 238
-- Name: ibilling_constant_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY ibilling_constant
    ADD CONSTRAINT ibilling_constant_pkey PRIMARY KEY (id);
    
--
-- TOC entry 2356 (class 2606 OID 35118)
-- Dependencies: 192 192 192 192 192
-- Name: international_description_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY international_description
    ADD CONSTRAINT international_description_pkey PRIMARY KEY (table_id, foreign_id, psudo_column, language_id);


--
-- TOC entry 2366 (class 2606 OID 35120)
-- Dependencies: 194 194
-- Name: invoice_delivery_method_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY invoice_delivery_method
    ADD CONSTRAINT invoice_delivery_method_pkey PRIMARY KEY (id);


--
-- TOC entry 2368 (class 2606 OID 35122)
-- Dependencies: 195 195
-- Name: invoice_line_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY invoice_line
    ADD CONSTRAINT invoice_line_pkey PRIMARY KEY (id);


--
-- TOC entry 2371 (class 2606 OID 35124)
-- Dependencies: 196 196
-- Name: invoice_line_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY invoice_line_type
    ADD CONSTRAINT invoice_line_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2358 (class 2606 OID 35126)
-- Dependencies: 193 193
-- Name: invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 2373 (class 2606 OID 35128)
-- Dependencies: 197 197
-- Name: item_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- TOC entry 2378 (class 2606 OID 35130)
-- Dependencies: 200 200 200
-- Name: item_type_exclude_map_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY item_type_exclude_map
    ADD CONSTRAINT item_type_exclude_map_pkey PRIMARY KEY (item_id, type_id);


--
-- TOC entry 2376 (class 2606 OID 35132)
-- Dependencies: 199 199
-- Name: item_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY item_type
    ADD CONSTRAINT item_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2380 (class 2606 OID 35134)
-- Dependencies: 203 203
-- Name: ibilling_table_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY ibilling_table
    ADD CONSTRAINT ibilling_table_pkey PRIMARY KEY (id);


--
-- TOC entry 2382 (class 2606 OID 35136)
-- Dependencies: 204 204
-- Name: language_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);


--
-- TOC entry 2384 (class 2606 OID 35138)
-- Dependencies: 205 205
-- Name: mediation_cfg_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY mediation_cfg
    ADD CONSTRAINT mediation_cfg_pkey PRIMARY KEY (id);


--
-- TOC entry 2386 (class 2606 OID 35140)
-- Dependencies: 206 206
-- Name: mediation_errors_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY mediation_errors
    ADD CONSTRAINT mediation_errors_pkey PRIMARY KEY (accountcode);


--
-- TOC entry 2388 (class 2606 OID 35142)
-- Dependencies: 208 208
-- Name: mediation_process_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY mediation_process
    ADD CONSTRAINT mediation_process_pkey PRIMARY KEY (id);


--
-- TOC entry 2394 (class 2606 OID 35144)
-- Dependencies: 210 210
-- Name: mediation_record_line_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY mediation_record_line
    ADD CONSTRAINT mediation_record_line_pkey PRIMARY KEY (id);


--
-- TOC entry 2391 (class 2606 OID 35146)
-- Dependencies: 209 209
-- Name: mediation_record_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY mediation_record
    ADD CONSTRAINT mediation_record_pkey PRIMARY KEY (id);


--
-- TOC entry 2396 (class 2606 OID 35148)
-- Dependencies: 211 211
-- Name: notification_category_pk; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_category
    ADD CONSTRAINT notification_category_pk PRIMARY KEY (id);


--
-- TOC entry 2402 (class 2606 OID 35150)
-- Dependencies: 214 214
-- Name: notification_message_arch_line_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message_arch_line
    ADD CONSTRAINT notification_message_arch_line_pkey PRIMARY KEY (id);


--
-- TOC entry 2400 (class 2606 OID 35152)
-- Dependencies: 213 213
-- Name: notification_message_arch_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message_arch
    ADD CONSTRAINT notification_message_arch_pkey PRIMARY KEY (id);


--
-- TOC entry 2404 (class 2606 OID 35154)
-- Dependencies: 215 215
-- Name: notification_message_line_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message_line
    ADD CONSTRAINT notification_message_line_pkey PRIMARY KEY (id);


--
-- TOC entry 2398 (class 2606 OID 35156)
-- Dependencies: 212 212
-- Name: notification_message_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message
    ADD CONSTRAINT notification_message_pkey PRIMARY KEY (id);


--
-- TOC entry 2406 (class 2606 OID 35158)
-- Dependencies: 216 216
-- Name: notification_message_section_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message_section
    ADD CONSTRAINT notification_message_section_pkey PRIMARY KEY (id);


--
-- TOC entry 2408 (class 2606 OID 35160)
-- Dependencies: 217 217
-- Name: notification_message_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY notification_message_type
    ADD CONSTRAINT notification_message_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2410 (class 2606 OID 35162)
-- Dependencies: 218 218
-- Name: order_billing_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY order_billing_type
    ADD CONSTRAINT order_billing_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2414 (class 2606 OID 35164)
-- Dependencies: 219 219
-- Name: order_line_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY order_line
    ADD CONSTRAINT order_line_pkey PRIMARY KEY (id);


--
-- TOC entry 2416 (class 2606 OID 35166)
-- Dependencies: 220 220
-- Name: order_line_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY order_line_type
    ADD CONSTRAINT order_line_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2418 (class 2606 OID 35168)
-- Dependencies: 221 221
-- Name: order_period_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY order_period
    ADD CONSTRAINT order_period_pkey PRIMARY KEY (id);


--
-- TOC entry 2423 (class 2606 OID 35170)
-- Dependencies: 222 222
-- Name: order_process_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY order_process
    ADD CONSTRAINT order_process_pkey PRIMARY KEY (id);


--
-- TOC entry 2425 (class 2606 OID 35172)
-- Dependencies: 223 223
-- Name: paper_invoice_batch_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY paper_invoice_batch
    ADD CONSTRAINT paper_invoice_batch_pkey PRIMARY KEY (id);


--
-- TOC entry 2431 (class 2606 OID 35174)
-- Dependencies: 225 225
-- Name: partner_payout_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY partner_payout
    ADD CONSTRAINT partner_payout_pkey PRIMARY KEY (id);


--
-- TOC entry 2428 (class 2606 OID 35176)
-- Dependencies: 224 224
-- Name: partner_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY partner
    ADD CONSTRAINT partner_pkey PRIMARY KEY (id);


--
-- TOC entry 2434 (class 2606 OID 35178)
-- Dependencies: 226 226
-- Name: partner_range_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY partner_range
    ADD CONSTRAINT partner_range_pkey PRIMARY KEY (id);


--
-- TOC entry 2442 (class 2606 OID 35180)
-- Dependencies: 228 228
-- Name: payment_authorization_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment_authorization
    ADD CONSTRAINT payment_authorization_pkey PRIMARY KEY (id);


--
-- TOC entry 2445 (class 2606 OID 35182)
-- Dependencies: 229 229
-- Name: payment_info_cheque_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment_info_cheque
    ADD CONSTRAINT payment_info_cheque_pkey PRIMARY KEY (id);


--
-- TOC entry 2448 (class 2606 OID 35184)
-- Dependencies: 230 230
-- Name: payment_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment_invoice
    ADD CONSTRAINT payment_invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 2450 (class 2606 OID 35186)
-- Dependencies: 231 231
-- Name: payment_method_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment_method
    ADD CONSTRAINT payment_method_pkey PRIMARY KEY (id);


--
-- TOC entry 2438 (class 2606 OID 35188)
-- Dependencies: 227 227
-- Name: payment_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (id);


--
-- TOC entry 2452 (class 2606 OID 35190)
-- Dependencies: 232 232
-- Name: payment_result_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY payment_result
    ADD CONSTRAINT payment_result_pkey PRIMARY KEY (id);


--
-- TOC entry 2454 (class 2606 OID 35192)
-- Dependencies: 233 233
-- Name: period_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY period_unit
    ADD CONSTRAINT period_unit_pkey PRIMARY KEY (id);


--
-- TOC entry 2456 (class 2606 OID 35194)
-- Dependencies: 234 234
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- TOC entry 2459 (class 2606 OID 35196)
-- Dependencies: 236 236
-- Name: permission_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY permission_type
    ADD CONSTRAINT permission_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2462 (class 2606 OID 35198)
-- Dependencies: 237 237
-- Name: permission_user_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY permission_user
    ADD CONSTRAINT permission_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2466 (class 2606 OID 35200)
-- Dependencies: 239 239
-- Name: pluggable_task_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY pluggable_task_parameter
    ADD CONSTRAINT pluggable_task_parameter_pkey PRIMARY KEY (id);


--
-- TOC entry 2464 (class 2606 OID 35202)
-- Dependencies: 238 238
-- Name: pluggable_task_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY pluggable_task
    ADD CONSTRAINT pluggable_task_pkey PRIMARY KEY (id);


--
-- TOC entry 2470 (class 2606 OID 35204)
-- Dependencies: 241 241
-- Name: pluggable_task_type_category_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY pluggable_task_type_category
    ADD CONSTRAINT pluggable_task_type_category_pkey PRIMARY KEY (id);


--
-- TOC entry 2468 (class 2606 OID 35206)
-- Dependencies: 240 240
-- Name: pluggable_task_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY pluggable_task_type
    ADD CONSTRAINT pluggable_task_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2472 (class 2606 OID 35208)
-- Dependencies: 242 242
-- Name: preference_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY preference
    ADD CONSTRAINT preference_pkey PRIMARY KEY (id);


--
-- TOC entry 2474 (class 2606 OID 35210)
-- Dependencies: 243 243
-- Name: preference_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY preference_type
    ADD CONSTRAINT preference_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2477 (class 2606 OID 35212)
-- Dependencies: 244 244
-- Name: process_run_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY process_run
    ADD CONSTRAINT process_run_pkey PRIMARY KEY (id);


--
-- TOC entry 2480 (class 2606 OID 35214)
-- Dependencies: 245 245
-- Name: process_run_total_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY process_run_total
    ADD CONSTRAINT process_run_total_pkey PRIMARY KEY (id);


--
-- TOC entry 2483 (class 2606 OID 35216)
-- Dependencies: 246 246
-- Name: process_run_total_pm_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY process_run_total_pm
    ADD CONSTRAINT process_run_total_pm_pkey PRIMARY KEY (id);


--
-- TOC entry 2485 (class 2606 OID 35218)
-- Dependencies: 247 247
-- Name: process_run_user_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY process_run_user
    ADD CONSTRAINT process_run_user_pkey PRIMARY KEY (id);


--
-- TOC entry 2488 (class 2606 OID 35220)
-- Dependencies: 248 248
-- Name: promotion_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY promotion
    ADD CONSTRAINT promotion_pkey PRIMARY KEY (id);


--
-- TOC entry 2494 (class 2606 OID 35222)
-- Dependencies: 250 250
-- Name: purchase_order_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_pkey PRIMARY KEY (id);


--
-- TOC entry 2496 (class 2606 OID 35224)
-- Dependencies: 251 251
-- Name: recent_item_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY recent_item
    ADD CONSTRAINT recent_item_pkey PRIMARY KEY (id);


--
-- TOC entry 2500 (class 2606 OID 35226)
-- Dependencies: 253 253
-- Name: report_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY report_parameter
    ADD CONSTRAINT report_parameter_pkey PRIMARY KEY (id);


--
-- TOC entry 2498 (class 2606 OID 35228)
-- Dependencies: 252 252
-- Name: report_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY report
    ADD CONSTRAINT report_pkey PRIMARY KEY (id);


--
-- TOC entry 2502 (class 2606 OID 35230)
-- Dependencies: 254 254
-- Name: report_type_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY report_type
    ADD CONSTRAINT report_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2504 (class 2606 OID 35232)
-- Dependencies: 255 255
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2506 (class 2606 OID 35234)
-- Dependencies: 256 256
-- Name: shortcut_pkey; Type: CONSTRAINT; Schema: public; Owner: ibilling; Tablespace: 
--

ALTER TABLE ONLY shortcut
    ADD CONSTRAINT shortcut_pkey PRIMARY KEY (id);


--
-- TOC entry 2280 (class 1259 OID 35235)
-- Dependencies: 161
-- Name: ach_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ach_i_2 ON ach USING btree (user_id);


--
-- TOC entry 2481 (class 1259 OID 35236)
-- Dependencies: 246
-- Name: bp_pm_index_total; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX bp_pm_index_total ON process_run_total_pm USING btree (process_run_total_id);


--
-- TOC entry 2475 (class 1259 OID 35237)
-- Dependencies: 244
-- Name: bp_run_process_ix; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX bp_run_process_ix ON process_run USING btree (process_id);


--
-- TOC entry 2478 (class 1259 OID 35238)
-- Dependencies: 245
-- Name: bp_run_total_run_ix; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX bp_run_total_run_ix ON process_run_total USING btree (process_run_id);


--
-- TOC entry 2300 (class 1259 OID 35239)
-- Dependencies: 169
-- Name: contact_i_del; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX contact_i_del ON contact USING btree (deleted);


--
-- TOC entry 2316 (class 1259 OID 35240)
-- Dependencies: 172
-- Name: contact_map_i_1; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX contact_map_i_1 ON contact_map USING btree (contact_id);


--
-- TOC entry 2317 (class 1259 OID 35241)
-- Dependencies: 172 172 172
-- Name: contact_map_i_3; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX contact_map_i_3 ON contact_map USING btree (table_id, foreign_id, type_id);


--
-- TOC entry 2439 (class 1259 OID 35242)
-- Dependencies: 228
-- Name: create_datetime; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX create_datetime ON payment_authorization USING btree (create_datetime);


--
-- TOC entry 2330 (class 1259 OID 35243)
-- Dependencies: 177 177
-- Name: currency_entity_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX currency_entity_map_i_2 ON currency_entity_map USING btree (currency_id, entity_id);


--
-- TOC entry 2333 (class 1259 OID 35244)
-- Dependencies: 179
-- Name: customer_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX customer_i_2 ON customer USING btree (user_id);


--
-- TOC entry 2353 (class 1259 OID 35245)
-- Dependencies: 192
-- Name: int_description_i_lan; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX int_description_i_lan ON international_description USING btree (language_id);


--
-- TOC entry 2354 (class 1259 OID 35246)
-- Dependencies: 192 192 192
-- Name: international_description_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX international_description_i_2 ON international_description USING btree (table_id, foreign_id, language_id);


--
-- TOC entry 2287 (class 1259 OID 35247)
-- Dependencies: 163 163
-- Name: ix_base_user_un; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_base_user_un ON base_user USING btree (entity_id, user_name);


--
-- TOC entry 2294 (class 1259 OID 35248)
-- Dependencies: 166 166
-- Name: ix_blacklist_entity_type; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_blacklist_entity_type ON blacklist USING btree (entity_id, type);


--
-- TOC entry 2295 (class 1259 OID 35249)
-- Dependencies: 166 166
-- Name: ix_blacklist_user_type; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_blacklist_user_type ON blacklist USING btree (user_id, type);


--
-- TOC entry 2326 (class 1259 OID 35250)
-- Dependencies: 175
-- Name: ix_cc_number; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_cc_number ON credit_card USING btree (cc_number_plain);


--
-- TOC entry 2327 (class 1259 OID 35251)
-- Dependencies: 175
-- Name: ix_cc_number_encrypted; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_cc_number_encrypted ON credit_card USING btree (cc_number);


--
-- TOC entry 2315 (class 1259 OID 35252)
-- Dependencies: 171
-- Name: ix_cf_type_entity; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_cf_type_entity ON contact_field_type USING btree (entity_id);


--
-- TOC entry 2303 (class 1259 OID 35253)
-- Dependencies: 169 169 169 169 169 169
-- Name: ix_contact_address; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_address ON contact USING btree (street_addres1, city, postal_code, street_addres2, state_province, country_code);


--
-- TOC entry 2311 (class 1259 OID 35254)
-- Dependencies: 170
-- Name: ix_contact_field_cid; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_field_cid ON contact_field USING btree (contact_id);


--
-- TOC entry 2312 (class 1259 OID 35255)
-- Dependencies: 170
-- Name: ix_contact_field_content; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_field_content ON contact_field USING btree (content);


--
-- TOC entry 2304 (class 1259 OID 35256)
-- Dependencies: 169
-- Name: ix_contact_fname; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_fname ON contact USING btree (first_name);


--
-- TOC entry 2305 (class 1259 OID 35257)
-- Dependencies: 169 169
-- Name: ix_contact_fname_lname; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_fname_lname ON contact USING btree (first_name, last_name);


--
-- TOC entry 2306 (class 1259 OID 35258)
-- Dependencies: 169
-- Name: ix_contact_lname; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_lname ON contact USING btree (last_name);


--
-- TOC entry 2307 (class 1259 OID 35259)
-- Dependencies: 169
-- Name: ix_contact_orgname; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_orgname ON contact USING btree (organization_name);


--
-- TOC entry 2308 (class 1259 OID 35260)
-- Dependencies: 169 169 169
-- Name: ix_contact_phone; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_contact_phone ON contact USING btree (phone_phone_number, phone_area_code, phone_country_code);


--
-- TOC entry 2340 (class 1259 OID 35261)
-- Dependencies: 184 184 184
-- Name: ix_el_main; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_el_main ON event_log USING btree (module_id, message_id, create_datetime);


--
-- TOC entry 2359 (class 1259 OID 35262)
-- Dependencies: 193
-- Name: ix_invoice_date; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_date ON invoice USING btree (create_datetime);


--
-- TOC entry 2360 (class 1259 OID 35263)
-- Dependencies: 193 193
-- Name: ix_invoice_due_date; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_due_date ON invoice USING btree (user_id, due_date);


--
-- TOC entry 2369 (class 1259 OID 35264)
-- Dependencies: 195
-- Name: ix_invoice_line_invoice_id; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_line_invoice_id ON invoice_line USING btree (invoice_id);


--
-- TOC entry 2361 (class 1259 OID 35265)
-- Dependencies: 193 193
-- Name: ix_invoice_number; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_number ON invoice USING btree (user_id, public_number);


--
-- TOC entry 2362 (class 1259 OID 35266)
-- Dependencies: 193
-- Name: ix_invoice_process; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_process ON invoice USING btree (billing_process_id);


--
-- TOC entry 2363 (class 1259 OID 35267)
-- Dependencies: 193 193
-- Name: ix_invoice_ts; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_ts ON invoice USING btree (create_timestamp, user_id);


--
-- TOC entry 2364 (class 1259 OID 35268)
-- Dependencies: 193 193
-- Name: ix_invoice_user_id; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_invoice_user_id ON invoice USING btree (user_id, deleted);


--
-- TOC entry 2374 (class 1259 OID 35269)
-- Dependencies: 197 197
-- Name: ix_item_ent; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_item_ent ON item USING btree (entity_id, internal_number);


--
-- TOC entry 2392 (class 1259 OID 35270)
-- Dependencies: 210
-- Name: ix_mrl_order_line; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_mrl_order_line ON mediation_record_line USING btree (order_line_id);


--
-- TOC entry 2411 (class 1259 OID 35271)
-- Dependencies: 219
-- Name: ix_order_line_item_id; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_order_line_item_id ON order_line USING btree (item_id);


--
-- TOC entry 2412 (class 1259 OID 35272)
-- Dependencies: 219
-- Name: ix_order_line_order_id; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_order_line_order_id ON order_line USING btree (order_id);


--
-- TOC entry 2419 (class 1259 OID 35273)
-- Dependencies: 222
-- Name: ix_order_process_in; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_order_process_in ON order_process USING btree (invoice_id);


--
-- TOC entry 2440 (class 1259 OID 35274)
-- Dependencies: 228
-- Name: ix_pa_payment; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_pa_payment ON payment_authorization USING btree (payment_id);


--
-- TOC entry 2486 (class 1259 OID 35275)
-- Dependencies: 248
-- Name: ix_promotion_code; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_promotion_code ON promotion USING btree (code);


--
-- TOC entry 2490 (class 1259 OID 35276)
-- Dependencies: 250 250
-- Name: ix_purchase_order_date; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_purchase_order_date ON purchase_order USING btree (user_id, create_datetime);


--
-- TOC entry 2420 (class 1259 OID 35277)
-- Dependencies: 222 222
-- Name: ix_uq_order_process_or_bp; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_uq_order_process_or_bp ON order_process USING btree (order_id, billing_process_id);


--
-- TOC entry 2421 (class 1259 OID 35278)
-- Dependencies: 222 222
-- Name: ix_uq_order_process_or_in; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_uq_order_process_or_in ON order_process USING btree (order_id, invoice_id);


--
-- TOC entry 2446 (class 1259 OID 35279)
-- Dependencies: 230 230
-- Name: ix_uq_payment_inv_map_pa_in; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX ix_uq_payment_inv_map_pa_in ON payment_invoice USING btree (payment_id, invoice_id);


--
-- TOC entry 2389 (class 1259 OID 35280)
-- Dependencies: 209 209
-- Name: mediation_record_i; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX mediation_record_i ON mediation_record USING btree (id_key, status_id);


--
-- TOC entry 2426 (class 1259 OID 35281)
-- Dependencies: 224
-- Name: partner_i_3; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX partner_i_3 ON partner USING btree (user_id);


--
-- TOC entry 2429 (class 1259 OID 35282)
-- Dependencies: 225
-- Name: partner_payout_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX partner_payout_i_2 ON partner_payout USING btree (partner_id);


--
-- TOC entry 2432 (class 1259 OID 35283)
-- Dependencies: 226
-- Name: partner_range_p; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX partner_range_p ON partner_range USING btree (partner_id);


--
-- TOC entry 2435 (class 1259 OID 35284)
-- Dependencies: 227 227
-- Name: payment_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX payment_i_2 ON payment USING btree (user_id, create_datetime);


--
-- TOC entry 2436 (class 1259 OID 35285)
-- Dependencies: 227 227
-- Name: payment_i_3; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX payment_i_3 ON payment USING btree (user_id, balance);


--
-- TOC entry 2457 (class 1259 OID 35286)
-- Dependencies: 235 235
-- Name: permission_role_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX permission_role_map_i_2 ON permission_role_map USING btree (permission_id, role_id);


--
-- TOC entry 2460 (class 1259 OID 35287)
-- Dependencies: 237 237
-- Name: permission_user_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX permission_user_map_i_2 ON permission_user USING btree (permission_id, user_id);


--
-- TOC entry 2489 (class 1259 OID 35288)
-- Dependencies: 249 249
-- Name: promotion_user_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX promotion_user_map_i_2 ON promotion_user_map USING btree (user_id, promotion_id);


--
-- TOC entry 2491 (class 1259 OID 35289)
-- Dependencies: 250 250
-- Name: purchase_order_i_notif; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX purchase_order_i_notif ON purchase_order USING btree (active_until, notification_step);


--
-- TOC entry 2492 (class 1259 OID 35290)
-- Dependencies: 250 250
-- Name: purchase_order_i_user; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX purchase_order_i_user ON purchase_order USING btree (user_id, deleted);


--
-- TOC entry 2443 (class 1259 OID 35291)
-- Dependencies: 228
-- Name: transaction_id; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX transaction_id ON payment_authorization USING btree (transaction_id);


--
-- TOC entry 2507 (class 1259 OID 35292)
-- Dependencies: 257 257
-- Name: user_credit_card_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX user_credit_card_map_i_2 ON user_credit_card_map USING btree (user_id, credit_card_id);


--
-- TOC entry 2508 (class 1259 OID 35293)
-- Dependencies: 258 258
-- Name: user_role_map_i_2; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX user_role_map_i_2 ON user_role_map USING btree (user_id, role_id);


--
-- TOC entry 2509 (class 1259 OID 35294)
-- Dependencies: 258
-- Name: user_role_map_i_role; Type: INDEX; Schema: public; Owner: ibilling; Tablespace: 
--

CREATE INDEX user_role_map_i_role ON user_role_map USING btree (role_id);


--
-- TOC entry 2510 (class 2606 OID 35295)
-- Dependencies: 161 2285 163
-- Name: ach_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY ach
    ADD CONSTRAINT ach_fk_1 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2511 (class 2606 OID 35300)
-- Dependencies: 162 180 2336
-- Name: ageing_entity_step_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY ageing_entity_step
    ADD CONSTRAINT ageing_entity_step_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2512 (class 2606 OID 35305)
-- Dependencies: 163 180 2336
-- Name: base_user_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY base_user
    ADD CONSTRAINT base_user_fk_3 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2513 (class 2606 OID 35310)
-- Dependencies: 2381 163 204
-- Name: base_user_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY base_user
    ADD CONSTRAINT base_user_fk_4 FOREIGN KEY (language_id) REFERENCES language(id);


--
-- TOC entry 2514 (class 2606 OID 35315)
-- Dependencies: 176 163 2328
-- Name: base_user_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY base_user
    ADD CONSTRAINT base_user_fk_5 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2518 (class 2606 OID 35320)
-- Dependencies: 233 165 2453
-- Name: billing_process_configuration_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY billing_process_configuration
    ADD CONSTRAINT billing_process_configuration_fk_1 FOREIGN KEY (period_unit_id) REFERENCES period_unit(id);


--
-- TOC entry 2519 (class 2606 OID 35325)
-- Dependencies: 180 165 2336
-- Name: billing_process_configuration_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY billing_process_configuration
    ADD CONSTRAINT billing_process_configuration_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2515 (class 2606 OID 35330)
-- Dependencies: 2453 233 164
-- Name: billing_process_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY billing_process
    ADD CONSTRAINT billing_process_fk_1 FOREIGN KEY (period_unit_id) REFERENCES period_unit(id);


--
-- TOC entry 2516 (class 2606 OID 35335)
-- Dependencies: 164 180 2336
-- Name: billing_process_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY billing_process
    ADD CONSTRAINT billing_process_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2517 (class 2606 OID 35340)
-- Dependencies: 2424 164 223
-- Name: billing_process_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY billing_process
    ADD CONSTRAINT billing_process_fk_3 FOREIGN KEY (paper_invoice_batch_id) REFERENCES paper_invoice_batch(id);


--
-- TOC entry 2520 (class 2606 OID 35345)
-- Dependencies: 2336 166 180
-- Name: blacklist_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY blacklist
    ADD CONSTRAINT blacklist_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2521 (class 2606 OID 35350)
-- Dependencies: 2285 166 163
-- Name: blacklist_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY blacklist
    ADD CONSTRAINT blacklist_fk_2 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2580 (class 2606 OID 35355)
-- Dependencies: 217 211 2395
-- Name: category_id_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message_type
    ADD CONSTRAINT category_id_fk_1 FOREIGN KEY (category_id) REFERENCES notification_category(id);


--
-- TOC entry 2522 (class 2606 OID 35360)
-- Dependencies: 2301 170 169
-- Name: contact_field_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_field
    ADD CONSTRAINT contact_field_fk_1 FOREIGN KEY (contact_id) REFERENCES contact(id);


--
-- TOC entry 2523 (class 2606 OID 35365)
-- Dependencies: 2313 171 170
-- Name: contact_field_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_field
    ADD CONSTRAINT contact_field_fk_2 FOREIGN KEY (type_id) REFERENCES contact_field_type(id);


--
-- TOC entry 2524 (class 2606 OID 35370)
-- Dependencies: 2336 180 171
-- Name: contact_field_type_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_field_type
    ADD CONSTRAINT contact_field_type_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2525 (class 2606 OID 35375)
-- Dependencies: 2379 203 172
-- Name: contact_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_map
    ADD CONSTRAINT contact_map_fk_1 FOREIGN KEY (table_id) REFERENCES ibilling_table(id);


--
-- TOC entry 2526 (class 2606 OID 35380)
-- Dependencies: 172 2320 173
-- Name: contact_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_map
    ADD CONSTRAINT contact_map_fk_2 FOREIGN KEY (type_id) REFERENCES contact_type(id);


--
-- TOC entry 2527 (class 2606 OID 35385)
-- Dependencies: 169 172 2301
-- Name: contact_map_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_map
    ADD CONSTRAINT contact_map_fk_3 FOREIGN KEY (contact_id) REFERENCES contact(id);


--
-- TOC entry 2528 (class 2606 OID 35390)
-- Dependencies: 173 2336 180
-- Name: contact_type_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY contact_type
    ADD CONSTRAINT contact_type_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2529 (class 2606 OID 35395)
-- Dependencies: 2336 180 177
-- Name: currency_entity_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY currency_entity_map
    ADD CONSTRAINT currency_entity_map_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2530 (class 2606 OID 35400)
-- Dependencies: 2328 177 176
-- Name: currency_entity_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY currency_entity_map
    ADD CONSTRAINT currency_entity_map_fk_2 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2531 (class 2606 OID 35405)
-- Dependencies: 176 2328 178
-- Name: currency_exchange_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY currency_exchange
    ADD CONSTRAINT currency_exchange_fk_1 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2532 (class 2606 OID 35410)
-- Dependencies: 194 179 2365
-- Name: customer_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_fk_1 FOREIGN KEY (invoice_delivery_method_id) REFERENCES invoice_delivery_method(id);


--
-- TOC entry 2533 (class 2606 OID 35415)
-- Dependencies: 224 179 2427
-- Name: customer_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_fk_2 FOREIGN KEY (partner_id) REFERENCES partner(id);


--
-- TOC entry 2534 (class 2606 OID 35420)
-- Dependencies: 179 163 2285
-- Name: customer_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_fk_3 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2537 (class 2606 OID 35425)
-- Dependencies: 180 2336 181
-- Name: entity_delivery_method_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_delivery_method_map
    ADD CONSTRAINT entity_delivery_method_map_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2538 (class 2606 OID 35430)
-- Dependencies: 181 194 2365
-- Name: entity_delivery_method_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_delivery_method_map
    ADD CONSTRAINT entity_delivery_method_map_fk_2 FOREIGN KEY (method_id) REFERENCES invoice_delivery_method(id);


--
-- TOC entry 2535 (class 2606 OID 35435)
-- Dependencies: 2328 176 180
-- Name: entity_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_fk_1 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2536 (class 2606 OID 35440)
-- Dependencies: 180 204 2381
-- Name: entity_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_fk_2 FOREIGN KEY (language_id) REFERENCES language(id);


--
-- TOC entry 2539 (class 2606 OID 35445)
-- Dependencies: 182 231 2449
-- Name: entity_payment_method_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_payment_method_map
    ADD CONSTRAINT entity_payment_method_map_fk_1 FOREIGN KEY (payment_method_id) REFERENCES payment_method(id);


--
-- TOC entry 2540 (class 2606 OID 35450)
-- Dependencies: 2336 182 180
-- Name: entity_payment_method_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_payment_method_map
    ADD CONSTRAINT entity_payment_method_map_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2543 (class 2606 OID 35455)
-- Dependencies: 2343 184 186
-- Name: event_log_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_1 FOREIGN KEY (module_id) REFERENCES event_log_module(id);


--
-- TOC entry 2544 (class 2606 OID 35460)
-- Dependencies: 184 2336 180
-- Name: event_log_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2545 (class 2606 OID 35465)
-- Dependencies: 2285 184 163
-- Name: event_log_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_3 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2546 (class 2606 OID 35470)
-- Dependencies: 184 203 2379
-- Name: event_log_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_4 FOREIGN KEY (table_id) REFERENCES ibilling_table(id);


--
-- TOC entry 2547 (class 2606 OID 35475)
-- Dependencies: 184 185 2341
-- Name: event_log_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_5 FOREIGN KEY (message_id) REFERENCES event_log_message(id);


--
-- TOC entry 2548 (class 2606 OID 35480)
-- Dependencies: 184 2285 163
-- Name: event_log_fk_6; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY event_log
    ADD CONSTRAINT event_log_fk_6 FOREIGN KEY (affected_user_id) REFERENCES base_user(id);


--
-- TOC entry 2549 (class 2606 OID 35485)
-- Dependencies: 191 2351 190
-- Name: generic_status_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY generic_status
    ADD CONSTRAINT generic_status_fk_1 FOREIGN KEY (dtype) REFERENCES generic_status_type(id);


--
-- TOC entry 2550 (class 2606 OID 35490)
-- Dependencies: 192 2381 204
-- Name: international_description_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY international_description
    ADD CONSTRAINT international_description_fk_1 FOREIGN KEY (language_id) REFERENCES language(id);


--
-- TOC entry 2551 (class 2606 OID 35495)
-- Dependencies: 164 193 2288
-- Name: invoice_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT invoice_fk_1 FOREIGN KEY (billing_process_id) REFERENCES billing_process(id);


--
-- TOC entry 2552 (class 2606 OID 35500)
-- Dependencies: 2424 223 193
-- Name: invoice_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT invoice_fk_2 FOREIGN KEY (paper_invoice_batch_id) REFERENCES paper_invoice_batch(id);


--
-- TOC entry 2553 (class 2606 OID 35505)
-- Dependencies: 2328 176 193
-- Name: invoice_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT invoice_fk_3 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2554 (class 2606 OID 35510)
-- Dependencies: 193 193 2357
-- Name: invoice_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT invoice_fk_4 FOREIGN KEY (delegated_invoice_id) REFERENCES invoice(id);


--
-- TOC entry 2555 (class 2606 OID 35515)
-- Dependencies: 193 2357 195
-- Name: invoice_line_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice_line
    ADD CONSTRAINT invoice_line_fk_1 FOREIGN KEY (invoice_id) REFERENCES invoice(id);


--
-- TOC entry 2556 (class 2606 OID 35520)
-- Dependencies: 197 195 2372
-- Name: invoice_line_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice_line
    ADD CONSTRAINT invoice_line_fk_2 FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2557 (class 2606 OID 35525)
-- Dependencies: 2370 195 196
-- Name: invoice_line_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY invoice_line
    ADD CONSTRAINT invoice_line_fk_3 FOREIGN KEY (type_id) REFERENCES invoice_line_type(id);


--
-- TOC entry 2558 (class 2606 OID 35530)
-- Dependencies: 197 2336 180
-- Name: item_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2559 (class 2606 OID 35535)
-- Dependencies: 198 176 2328
-- Name: item_price_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_price
    ADD CONSTRAINT item_price_fk_1 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2560 (class 2606 OID 35540)
-- Dependencies: 198 2372 197
-- Name: item_price_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_price
    ADD CONSTRAINT item_price_fk_2 FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2562 (class 2606 OID 35545)
-- Dependencies: 197 2372 200
-- Name: item_type_exclude_item_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_type_exclude_map
    ADD CONSTRAINT item_type_exclude_item_id_fk FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2563 (class 2606 OID 35550)
-- Dependencies: 199 200 2375
-- Name: item_type_exclude_type_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_type_exclude_map
    ADD CONSTRAINT item_type_exclude_type_id_fk FOREIGN KEY (type_id) REFERENCES item_type(id);


--
-- TOC entry 2561 (class 2606 OID 35555)
-- Dependencies: 180 2336 199
-- Name: item_type_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_type
    ADD CONSTRAINT item_type_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2564 (class 2606 OID 35560)
-- Dependencies: 201 2372 197
-- Name: item_type_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_type_map
    ADD CONSTRAINT item_type_map_fk_1 FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2565 (class 2606 OID 35565)
-- Dependencies: 199 2375 201
-- Name: item_type_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY item_type_map
    ADD CONSTRAINT item_type_map_fk_2 FOREIGN KEY (type_id) REFERENCES item_type(id);


--
-- TOC entry 2566 (class 2606 OID 35570)
-- Dependencies: 2463 205 238
-- Name: mediation_cfg_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_cfg
    ADD CONSTRAINT mediation_cfg_fk_1 FOREIGN KEY (pluggable_task_id) REFERENCES pluggable_task(id);


--
-- TOC entry 2567 (class 2606 OID 35575)
-- Dependencies: 207 208 2387
-- Name: mediation_order_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_order_map
    ADD CONSTRAINT mediation_order_map_fk_1 FOREIGN KEY (mediation_process_id) REFERENCES mediation_process(id);


--
-- TOC entry 2568 (class 2606 OID 35580)
-- Dependencies: 2493 207 250
-- Name: mediation_order_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_order_map
    ADD CONSTRAINT mediation_order_map_fk_2 FOREIGN KEY (order_id) REFERENCES purchase_order(id);


--
-- TOC entry 2569 (class 2606 OID 35585)
-- Dependencies: 2383 208 205
-- Name: mediation_process_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_process
    ADD CONSTRAINT mediation_process_fk_1 FOREIGN KEY (configuration_id) REFERENCES mediation_cfg(id);


--
-- TOC entry 2570 (class 2606 OID 35590)
-- Dependencies: 209 2387 208
-- Name: mediation_record_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_record
    ADD CONSTRAINT mediation_record_fk_1 FOREIGN KEY (mediation_process_id) REFERENCES mediation_process(id);


--
-- TOC entry 2571 (class 2606 OID 35595)
-- Dependencies: 190 209 2349
-- Name: mediation_record_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_record
    ADD CONSTRAINT mediation_record_fk_2 FOREIGN KEY (status_id) REFERENCES generic_status(id);


--
-- TOC entry 2572 (class 2606 OID 35600)
-- Dependencies: 210 209 2390
-- Name: mediation_record_line_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_record_line
    ADD CONSTRAINT mediation_record_line_fk_1 FOREIGN KEY (mediation_record_id) REFERENCES mediation_record(id);


--
-- TOC entry 2573 (class 2606 OID 35605)
-- Dependencies: 210 219 2413
-- Name: mediation_record_line_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY mediation_record_line
    ADD CONSTRAINT mediation_record_line_fk_2 FOREIGN KEY (order_line_id) REFERENCES order_line(id);


--
-- TOC entry 2577 (class 2606 OID 35610)
-- Dependencies: 214 213 2399
-- Name: notif_mess_arch_line_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message_arch_line
    ADD CONSTRAINT notif_mess_arch_line_fk_1 FOREIGN KEY (message_archive_id) REFERENCES notification_message_arch(id);


--
-- TOC entry 2574 (class 2606 OID 35615)
-- Dependencies: 212 204 2381
-- Name: notification_message_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message
    ADD CONSTRAINT notification_message_fk_1 FOREIGN KEY (language_id) REFERENCES language(id);


--
-- TOC entry 2575 (class 2606 OID 35620)
-- Dependencies: 217 212 2407
-- Name: notification_message_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message
    ADD CONSTRAINT notification_message_fk_2 FOREIGN KEY (type_id) REFERENCES notification_message_type(id);


--
-- TOC entry 2576 (class 2606 OID 35625)
-- Dependencies: 180 212 2336
-- Name: notification_message_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message
    ADD CONSTRAINT notification_message_fk_3 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2578 (class 2606 OID 35630)
-- Dependencies: 215 2405 216
-- Name: notification_message_line_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message_line
    ADD CONSTRAINT notification_message_line_fk_1 FOREIGN KEY (message_section_id) REFERENCES notification_message_section(id);


--
-- TOC entry 2579 (class 2606 OID 35635)
-- Dependencies: 2397 212 216
-- Name: notification_message_section_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY notification_message_section
    ADD CONSTRAINT notification_message_section_fk_1 FOREIGN KEY (message_id) REFERENCES notification_message(id);


--
-- TOC entry 2581 (class 2606 OID 35640)
-- Dependencies: 219 2372 197
-- Name: order_line_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_line
    ADD CONSTRAINT order_line_fk_1 FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2582 (class 2606 OID 35645)
-- Dependencies: 250 2493 219
-- Name: order_line_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_line
    ADD CONSTRAINT order_line_fk_2 FOREIGN KEY (order_id) REFERENCES purchase_order(id);


--
-- TOC entry 2583 (class 2606 OID 35650)
-- Dependencies: 219 2415 220
-- Name: order_line_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_line
    ADD CONSTRAINT order_line_fk_3 FOREIGN KEY (type_id) REFERENCES order_line_type(id);


--
-- TOC entry 2584 (class 2606 OID 35655)
-- Dependencies: 221 180 2336
-- Name: order_period_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_period
    ADD CONSTRAINT order_period_fk_1 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2585 (class 2606 OID 35660)
-- Dependencies: 221 233 2453
-- Name: order_period_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_period
    ADD CONSTRAINT order_period_fk_2 FOREIGN KEY (unit_id) REFERENCES period_unit(id);


--
-- TOC entry 2586 (class 2606 OID 35665)
-- Dependencies: 222 250 2493
-- Name: order_process_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY order_process
    ADD CONSTRAINT order_process_fk_1 FOREIGN KEY (order_id) REFERENCES purchase_order(id);


--
-- TOC entry 2587 (class 2606 OID 35670)
-- Dependencies: 224 233 2453
-- Name: partner_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY partner
    ADD CONSTRAINT partner_fk_1 FOREIGN KEY (period_unit_id) REFERENCES period_unit(id);


--
-- TOC entry 2588 (class 2606 OID 35675)
-- Dependencies: 224 176 2328
-- Name: partner_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY partner
    ADD CONSTRAINT partner_fk_2 FOREIGN KEY (fee_currency_id) REFERENCES currency(id);


--
-- TOC entry 2589 (class 2606 OID 35680)
-- Dependencies: 224 163 2285
-- Name: partner_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY partner
    ADD CONSTRAINT partner_fk_3 FOREIGN KEY (related_clerk) REFERENCES base_user(id);


--
-- TOC entry 2590 (class 2606 OID 35685)
-- Dependencies: 224 163 2285
-- Name: partner_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY partner
    ADD CONSTRAINT partner_fk_4 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2591 (class 2606 OID 35690)
-- Dependencies: 225 224 2427
-- Name: partner_payout_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY partner_payout
    ADD CONSTRAINT partner_payout_fk_1 FOREIGN KEY (partner_id) REFERENCES partner(id);


--
-- TOC entry 2598 (class 2606 OID 35695)
-- Dependencies: 228 227 2437
-- Name: payment_authorization_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment_authorization
    ADD CONSTRAINT payment_authorization_fk_1 FOREIGN KEY (payment_id) REFERENCES payment(id);


--
-- TOC entry 2592 (class 2606 OID 35700)
-- Dependencies: 227 161 2281
-- Name: payment_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_1 FOREIGN KEY (ach_id) REFERENCES ach(id);


--
-- TOC entry 2593 (class 2606 OID 35705)
-- Dependencies: 227 2328 176
-- Name: payment_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_2 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2594 (class 2606 OID 35710)
-- Dependencies: 227 227 2437
-- Name: payment_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_3 FOREIGN KEY (payment_id) REFERENCES payment(id);


--
-- TOC entry 2595 (class 2606 OID 35715)
-- Dependencies: 175 2324 227
-- Name: payment_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_4 FOREIGN KEY (credit_card_id) REFERENCES credit_card(id);


--
-- TOC entry 2596 (class 2606 OID 35720)
-- Dependencies: 232 227 2451
-- Name: payment_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_5 FOREIGN KEY (result_id) REFERENCES payment_result(id);


--
-- TOC entry 2597 (class 2606 OID 35725)
-- Dependencies: 227 231 2449
-- Name: payment_fk_6; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment
    ADD CONSTRAINT payment_fk_6 FOREIGN KEY (method_id) REFERENCES payment_method(id);


--
-- TOC entry 2599 (class 2606 OID 35730)
-- Dependencies: 229 2437 227
-- Name: payment_info_cheque_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment_info_cheque
    ADD CONSTRAINT payment_info_cheque_fk_1 FOREIGN KEY (payment_id) REFERENCES payment(id);


--
-- TOC entry 2600 (class 2606 OID 35735)
-- Dependencies: 193 2357 230
-- Name: payment_invoice_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment_invoice
    ADD CONSTRAINT payment_invoice_fk_1 FOREIGN KEY (invoice_id) REFERENCES invoice(id);


--
-- TOC entry 2601 (class 2606 OID 35740)
-- Dependencies: 230 227 2437
-- Name: payment_invoice_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY payment_invoice
    ADD CONSTRAINT payment_invoice_fk_2 FOREIGN KEY (payment_id) REFERENCES payment(id);


--
-- TOC entry 2602 (class 2606 OID 35745)
-- Dependencies: 234 2458 236
-- Name: permission_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_fk_1 FOREIGN KEY (type_id) REFERENCES permission_type(id);


--
-- TOC entry 2603 (class 2606 OID 35750)
-- Dependencies: 255 2503 235
-- Name: permission_role_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY permission_role_map
    ADD CONSTRAINT permission_role_map_fk_1 FOREIGN KEY (role_id) REFERENCES role(id);


--
-- TOC entry 2604 (class 2606 OID 35755)
-- Dependencies: 235 234 2455
-- Name: permission_role_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY permission_role_map
    ADD CONSTRAINT permission_role_map_fk_2 FOREIGN KEY (permission_id) REFERENCES permission(id);


--
-- TOC entry 2605 (class 2606 OID 35760)
-- Dependencies: 237 2285 163
-- Name: permission_user_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY permission_user
    ADD CONSTRAINT permission_user_fk_1 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2606 (class 2606 OID 35765)
-- Dependencies: 234 237 2455
-- Name: permission_user_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY permission_user
    ADD CONSTRAINT permission_user_fk_2 FOREIGN KEY (permission_id) REFERENCES permission(id);


--
-- TOC entry 2607 (class 2606 OID 35770)
-- Dependencies: 2467 240 238
-- Name: pluggable_task_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY pluggable_task
    ADD CONSTRAINT pluggable_task_fk_1 FOREIGN KEY (type_id) REFERENCES pluggable_task_type(id);


--
-- TOC entry 2608 (class 2606 OID 35775)
-- Dependencies: 2336 238 180
-- Name: pluggable_task_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY pluggable_task
    ADD CONSTRAINT pluggable_task_fk_2 FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2609 (class 2606 OID 35780)
-- Dependencies: 239 238 2463
-- Name: pluggable_task_parameter_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY pluggable_task_parameter
    ADD CONSTRAINT pluggable_task_parameter_fk_1 FOREIGN KEY (task_id) REFERENCES pluggable_task(id);


--
-- TOC entry 2610 (class 2606 OID 35785)
-- Dependencies: 241 2469 240
-- Name: pluggable_task_type_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY pluggable_task_type
    ADD CONSTRAINT pluggable_task_type_fk_1 FOREIGN KEY (category_id) REFERENCES pluggable_task_type_category(id);


--
-- TOC entry 2611 (class 2606 OID 35790)
-- Dependencies: 2473 243 242
-- Name: preference_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY preference
    ADD CONSTRAINT preference_fk_1 FOREIGN KEY (type_id) REFERENCES preference_type(id);


--
-- TOC entry 2612 (class 2606 OID 35795)
-- Dependencies: 2379 203 242
-- Name: preference_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY preference
    ADD CONSTRAINT preference_fk_2 FOREIGN KEY (table_id) REFERENCES ibilling_table(id);


--
-- TOC entry 2613 (class 2606 OID 35800)
-- Dependencies: 164 2288 244
-- Name: process_run_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run
    ADD CONSTRAINT process_run_fk_1 FOREIGN KEY (process_id) REFERENCES billing_process(id);


--
-- TOC entry 2614 (class 2606 OID 35805)
-- Dependencies: 244 2349 190
-- Name: process_run_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run
    ADD CONSTRAINT process_run_fk_2 FOREIGN KEY (status_id) REFERENCES generic_status(id);


--
-- TOC entry 2615 (class 2606 OID 35810)
-- Dependencies: 245 176 2328
-- Name: process_run_total_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run_total
    ADD CONSTRAINT process_run_total_fk_1 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2616 (class 2606 OID 35815)
-- Dependencies: 2476 245 244
-- Name: process_run_total_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run_total
    ADD CONSTRAINT process_run_total_fk_2 FOREIGN KEY (process_run_id) REFERENCES process_run(id);


--
-- TOC entry 2617 (class 2606 OID 35820)
-- Dependencies: 2449 231 246
-- Name: process_run_total_pm_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run_total_pm
    ADD CONSTRAINT process_run_total_pm_fk_1 FOREIGN KEY (payment_method_id) REFERENCES payment_method(id);


--
-- TOC entry 2618 (class 2606 OID 35825)
-- Dependencies: 2476 247 244
-- Name: process_run_user_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run_user
    ADD CONSTRAINT process_run_user_fk_1 FOREIGN KEY (process_run_id) REFERENCES process_run(id);


--
-- TOC entry 2619 (class 2606 OID 35830)
-- Dependencies: 163 247 2285
-- Name: process_run_user_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY process_run_user
    ADD CONSTRAINT process_run_user_fk_2 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2620 (class 2606 OID 35835)
-- Dependencies: 248 2372 197
-- Name: promotion_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY promotion
    ADD CONSTRAINT promotion_fk_1 FOREIGN KEY (item_id) REFERENCES item(id);


--
-- TOC entry 2621 (class 2606 OID 35840)
-- Dependencies: 2285 249 163
-- Name: promotion_user_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY promotion_user_map
    ADD CONSTRAINT promotion_user_map_fk_1 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2622 (class 2606 OID 35845)
-- Dependencies: 249 2487 248
-- Name: promotion_user_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY promotion_user_map
    ADD CONSTRAINT promotion_user_map_fk_2 FOREIGN KEY (promotion_id) REFERENCES promotion(id);


--
-- TOC entry 2623 (class 2606 OID 35850)
-- Dependencies: 250 2328 176
-- Name: purchase_order_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_fk_1 FOREIGN KEY (currency_id) REFERENCES currency(id);


--
-- TOC entry 2624 (class 2606 OID 35855)
-- Dependencies: 218 250 2409
-- Name: purchase_order_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_fk_2 FOREIGN KEY (billing_type_id) REFERENCES order_billing_type(id);


--
-- TOC entry 2625 (class 2606 OID 35860)
-- Dependencies: 250 221 2417
-- Name: purchase_order_fk_3; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_fk_3 FOREIGN KEY (period_id) REFERENCES order_period(id);


--
-- TOC entry 2626 (class 2606 OID 35865)
-- Dependencies: 2285 250 163
-- Name: purchase_order_fk_4; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_fk_4 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2627 (class 2606 OID 35870)
-- Dependencies: 250 163 2285
-- Name: purchase_order_fk_5; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY purchase_order
    ADD CONSTRAINT purchase_order_fk_5 FOREIGN KEY (created_by) REFERENCES base_user(id);


--
-- TOC entry 2541 (class 2606 OID 35875)
-- Dependencies: 2336 180 183
-- Name: report_map_entity_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_report_map
    ADD CONSTRAINT report_map_entity_id_fk FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2542 (class 2606 OID 35880)
-- Dependencies: 2497 252 183
-- Name: report_map_report_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY entity_report_map
    ADD CONSTRAINT report_map_report_id_fk FOREIGN KEY (report_id) REFERENCES report(id);


--
-- TOC entry 2628 (class 2606 OID 35885)
-- Dependencies: 2336 255 180
-- Name: role_entity_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_entity_id_fk FOREIGN KEY (entity_id) REFERENCES entity(id);


--
-- TOC entry 2629 (class 2606 OID 35890)
-- Dependencies: 255 2503 258
-- Name: user_role_map_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY user_role_map
    ADD CONSTRAINT user_role_map_fk_1 FOREIGN KEY (role_id) REFERENCES role(id);


--
-- TOC entry 2630 (class 2606 OID 35895)
-- Dependencies: 163 258 2285
-- Name: user_role_map_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: ibilling
--

ALTER TABLE ONLY user_role_map
    ADD CONSTRAINT user_role_map_fk_2 FOREIGN KEY (user_id) REFERENCES base_user(id);


--
-- TOC entry 2733 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2012-07-13 11:01:54

--
-- PostgreSQL database dump complete
--

