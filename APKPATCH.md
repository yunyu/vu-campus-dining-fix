## Patching the APK

Install APKtool, extract the APK, and replace the decodeJSON_others function in SpecificFunctions.s with the following Smali bytecode: 

    .method public decodeJSON_others(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        .locals 7
        .param p1, "s1"    # Ljava/lang/String;
        .param p2, "s2"    # Ljava/lang/String;

        .prologue
        const/4 v5, 0x0

        .line 21
        :try_start_0
        new-instance v6, Lorg/json/JSONObject;

        invoke-direct {v6, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

        invoke-virtual {v6, p2}, Lorg/json/JSONObject;->getJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

        move-result-object v3

        .line 22
        .local v3, "jsonArray":Lorg/json/JSONArray;
        invoke-virtual {v3}, Lorg/json/JSONArray;->length()I

        move-result v6

        new-array v4, v6, [Ljava/lang/String;

        .line 23
        .local v4, "returnVal":[Ljava/lang/String;
        const/4 v2, 0x0

        .local v2, "i":I
        :goto_0
        array-length v6, v4

        if-ge v2, v6, :cond_1

        .line 24
        invoke-virtual {v3, v2}, Lorg/json/JSONArray;->get(I)Ljava/lang/Object;

        move-result-object v1

        .line 25
        .local v1, "element":Ljava/lang/Object;
        if-nez v1, :cond_0

        move-object v6, v5

        :goto_1
        aput-object v6, v4, v2

        .line 23
        add-int/lit8 v2, v2, 0x1

        goto :goto_0

        .line 25
        :cond_0
        invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;
        :try_end_0
        .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

        move-result-object v6

        goto :goto_1

        .line 28
        .end local v1    # "element":Ljava/lang/Object;
        .end local v2    # "i":I
        .end local v3    # "jsonArray":Lorg/json/JSONArray;
        .end local v4    # "returnVal":[Ljava/lang/String;
        :catch_0
        move-exception v0

        .local v0, "e":Ljava/lang/Exception;
        move-object v4, v5

        .line 29
        .end local v0    # "e":Ljava/lang/Exception;
        :cond_1
        return-object v4
    .end method

Then repackage the APK using APKTool and re-sign it.