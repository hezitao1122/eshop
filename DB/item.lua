
local uri_args = ngx.req.get_uri_args()
local itemId = uri_args["id"]

local cache_ngx = ngx.shared.my_cache

local itemCacheKey = "item_info_"..itemId
local itemDescKey = "item_desc_"..itemId

local itemCache = cache_ngx:get(itemCacheKey)
local itemDescCache = cache_ngx:get(itemDescKey)

if itemCache == "" or itemCache == nil then
    local http = require("resty.http")
    local httpc = http.new()

    local resp , err = httpc:request_uri("http://192.168.31.2:8081",{
        method = "GET",
        path = "/item/get?id="..itemId,
        keepalive=false

    })
    
    itemCache = resp.body
    
    cache_ngx:set(itemCacheKey,itemCache,10*60)
end

if itemDescCache == "" or itemDescCache == nil then
    local http = require("resty.http")
    local httpc = http.new()

    local resp , err = httpc:request_uri("http://192.168.31.2:8081",{
        method = "GET",
        path = "/item/desc/get?id="..itemId,
        keepalive=false

    })
    itemDescCache = resp.body
    cache_ngx:set(itemDescKey,itemDescCache,10*60)
end

local cjson = require("cjson")

local itemCacheJSON = cjson.decode(itemCache)
local itemDescCacheJSON = cjson.decode(itemDescCache)

local context = {
    itemId        = itemCacheJSON.data.id,
    itemTitle     = itemCacheJSON.data.title,
    itemSellPoint = itemCacheJSON.data.SellPoint,
    itemPrice     = itemCacheJSON.data.price,
    itemBarcode   = itemCacheJSON.data.barcode,
    itemImage     = itemCacheJSON.data.image,
    itemDesc      = itemDescCacheJSON.data.itemDesc
}

local template = require("resty.template")
template.render("item.html", context)













    





   

